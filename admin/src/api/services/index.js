import taskService from './taskService'
import deployService from './deployService'
import cleanupService from './cleanupService'
import rackService from './rackService'
import slaveService from './slaveService'
import mesosService from './mesosService'
import authService from './authService'
import ipamService from './ipamService'
import queueService from './queueService'
import metricService from './metricService'
import systemService from './systemService'

export default {
    deployService,
    taskService,
    cleanupService,
    rackService,
    slaveService,
    mesosService,
    ipamService,
    queueService,
    authService,
    metricService,
    systemService
}
