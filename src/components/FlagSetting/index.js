import React, { useState, useEffect } from 'react'
import { Skeleton, Icon, Button, Modal, Input,Card as ACard, Progress, Empty, message } from 'antd'
import { getFlags, getTasks, setTasks } from '../../redux/actions/flagSetting'
import { connect } from 'react-redux'
import parseData from '../../utils/parseData'
import ajax from '../../config/ajax'
import Card from '../Card/index'
import BookMark from '../common/BookMark/index'
import debounce from '../../utils/debounce'
import './index.less'
function FlagSetting(props) {
    const [offset, setOffset] = useState(4)
    const [page, setPage] = useState(1)
    const [visible, setVisible] = useState(false)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [flagTitle, setFlagTitle] = useState('')
    const [flagContent, setFlagContent] = useState('')
    const [loadIndex, setLoadIndex] = useState(-1)
    const { isLoaded, flags, handleGetFlags, token, history, handleGetTasks, tasks } = props
    useEffect(() => {
        if (!flags.length) {
            handleGetFlags(offset, page)
        }
        handleGetTasks()
        return () => {
        }
    }, [])
    useEffect(() => {
        return () => {
            
        }
    }, [flags])

    const handleChangeAnother = debounce(function(e) {
        handleGetFlags(offset, page + 1)
        setPage(page + 1)
    }, 400, true)


    const handleSetCustomFlag = e => {
        if (!token) {
            Modal.confirm({
                title: '需要登录后添加，是否前往登录',
                onOk() {
                    history.push('/login')
                },
                onCancel() {
                    
                },
                okText:'确认',
                cancelText: '取消'
            })
            return
        } 
        setVisible(true)
    }

    const handleOk = () => {
        setConfirmLoading(true)
        setTimeout(() => {
            setConfirmLoading(false)
            setVisible(false)
        }, 2000)
    }

    const handleCancel = () => {
        setVisible(false)
    }


    const handleFlagTitleOnChange = e => {
        setFlagTitle(e.target.value)
    }
    const handleFlagContentOnChange = e => {
        setFlagContent(e.target.value)
    }


    const handlePunchFlag = async (index, e) => {
        setLoadIndex(index)
        const res = await ajax('/punchFlag', { id: tasks[index].punch_id }, 'post')
        const {code, msg} = parseData(res)
        if (code === 0) {
            setLoadIndex(-1)
            handleGetTasks()
        } else {
            setLoadIndex(-1)
            message.error(msg)
        }
    }
    return (
        <section className='setflag' >
            <BookMark content='添加今日Flag' width='140px' />
            <div className='setflag-left'>
                <div className='another' >
                    <div onClick={handleChangeAnother}>
                        <Icon type="sync" />
                        <span>换一批</span>
                    </div> 
                </div>
                <div className='setflag-flag' >
                    <Skeleton loading={isLoaded} active >
                        {
                            flags.map(item => {
                                return (
                                    <Card
                                        title={item.flag_title}
                                        content={item.flag_content}
                                        cover={item.flag_image}
                                        key={item.flag_id}
                                        token={token}
                                        history={history}
                                        id={item.flag_id}
                                    />
                                )
                            })
                        }
                    </Skeleton>
                </div>
                <Button icon="plus" className='setflag-add' size='large' onClick={handleSetCustomFlag}>
                    添加自定义Flag
                </Button>
            </div>
            <div className='setflag-right'>
                <h3>今日任务</h3>
                <Progress
                    className='setflag-task-progress'
                    strokeColor={{
                        from: '#108ee9',
                        to: '#87d068',
                    }}
                    percent={50}
                    status="active"
                />
                <div className='setflag-task'>
                    {
                        tasks.map((item, index) => {
                            return (
                                <ACard title={item.punch_title} bordered={true} className='setflag-task-title' key={item.punch_id}>
                                    <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                                        <span style={{marginRight: '10px'}}>{item.punch_content}</span>
                                        {
                                            item.is_true ? 
                                            <Icon type="check-circle" style={{color: '#52c41a', fontSize: '25px'}}/> : 
                                            <Button type="primary" icon="check" loading={index === loadIndex} onClick={handlePunchFlag.bind(this, index)}>打卡</Button>
                                        }
                                    </div>
                                </ACard>
                            )
                        })
                    }
                    {
                        tasks.length === 0 ? <Empty description='暂无任务'/> : null
                    }
                </div>

            </div>
            <Modal
                title="自定义Flag"
                visible={visible}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}
                cancelText='取消'
                okText='确认'
            >   
            <Input 
                placeholder="Flag标题" 
                value={flagTitle} 
                onChange={handleFlagTitleOnChange}
            />
            <Input.TextArea 
                placeholder="Flag内容" 
                autosize={{ minRows: 2, maxRows: 6 }} 
                style={{marginTop: '20px'}} 
                value={flagContent} 
                onChange={handleFlagContentOnChange}
            />
            </Modal>
        </section>
    )
}
const mapStateToProps = state => ({
    flags: state.flagSetting.flags,
    isLoaded: state.flagSetting.isLoaded,
    isSuccess: state.flagSetting.isSuccess,
    tasks: state.flagSetting.tasks,
    token: state.global.userInfo.token,
})
const mapDispatchToProps = dispatch => ({
    handleGetFlags(offet, page){
        dispatch(getFlags({offet, page}))
    },
    handleGetTasks() {
        dispatch(getTasks())
    },
})
export default connect(mapStateToProps, mapDispatchToProps)(FlagSetting)