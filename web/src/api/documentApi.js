import request from '@/utils/request'

// 查询巡查任务列表
export function listPatrol_task(query) {
    return request({
        url: '/farming/patrol_task/list',
        method: 'get',
        params: query
    })
}

// 查询巡查任务详细
export function getPatrol_task(taskId) {
    return request({
        url: '/farming/patrol_task/' + taskId,
        method: 'get'
    })
}

// 新增巡查任务
export function addPatrol_task(data) {
    return request({
        url: '/farming/patrol_task',
        method: 'post',
        data: data
    })
}

// 修改巡查任务
export function updatePatrol_task(data) {
    return request({
        url: '/farming/patrol_task',
        method: 'put',
        data: data
    })
}

// 删除巡查任务
export function delPatrol_task(taskId) {
    return request({
        url: '/farming/patrol_task/' + taskId,
        method: 'delete'
    })
}

// 导出巡查任务
export function exportPatrol_task(query) {
    return request({
        url: '/farming/patrol_task/export',
        method: 'get',
        params: query
    })
}

// 修改任务状态
export function changeTaskStatus(taskId, taskStatus) {
    const data = {
        taskId,
        taskStatus
    }
    return request({
        url: '/farming/patrol_task/changeStatus',
        method: 'put',
        data: data
    })
}
