import Vue from 'vue';
import Router from 'vue-router';
import Login from '../components/Login.vue'
import Home from '../components/layout/Home.vue'
import TaskManage from '../components/TaskManage.vue'
import TaskInfo from '../components/TaskInfo.vue'
import DeployManage from '../components/DeployManage.vue'
import AddDeploy from '../components/AddDeploy.vue'
import CleanupManage from '../components/CleanupManage.vue'
import RackManage from '../components/RackManage.vue'
import SlaveManage from '../components/SlaveManage.vue'
import SubnetManage from '../components/SubnetManage.vue'
import IPManage from '../components/IPManage.vue'
import DashBoard from '../components/DashBoard.vue'
import OffsetManage from '../components/OffsetManage.vue'
import EventManage from '../components/EventManage.vue'
import MetricManage from '../components/MetricManage.vue'
import SystemManage from '../components/SystemManage.vue'


Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/login',
            name: 'login',
            component: Login,
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            children: [
                {
                    path: '/listDeploy',
                    name: 'deploymanage',
                    component: DeployManage
                },
                {
                    path: '/addDeploy',
                    name: 'adddeploy',
                    component: AddDeploy
                },
                {
                    path: '/task',
                    name: 'taskmanage',
                    component: TaskManage
                }, {
                    path: '/taskInfo/:id',
                    name: 'taskinfo',
                    component: TaskInfo
                },
                {
                    path: '/cleanup',
                    name: 'cleanupmanage',
                    component: CleanupManage
                },
                {
                    path: '/rack',
                    name: 'rackmanage',
                    component: RackManage
                },
                {
                    path: '/slave',
                    name: 'slavemanage',
                    component: SlaveManage
                },
                {
                    path: '/subnet',
                    name: 'subnetmanage',
                    component: SubnetManage
                },
                {
                    path: '/ip',
                    name: 'ipmanage',
                    component: IPManage
                },
                {
                    path: '/dashBoard',
                    name: 'dashboard',
                    component: DashBoard
                },
                {
                    path: '/event',
                    name: 'event',
                    component: EventManage
                },
                {
                    path: '/offset',
                    name: 'offset',
                    component: OffsetManage
                },{
                    path: '/metric',
                    name: 'metric',
                    component: MetricManage
                },{
                    path: '/system',
                    name: 'system',
                    component: SystemManage
                }
            ]
        },
        {
            path: '/',
            redirect: '/system'
        },
    ],
    linkActiveClass: 'active'
})
