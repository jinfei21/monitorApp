<template>
    <div>
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{path:'/home'}">Deploy管理</el-breadcrumb-item>
                        <el-breadcrumb-item>添加Deploy</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
        </div>
        <br/>

        <div>
            <div>
                <el-form ref="form" :inline="true" :model="deploy" label-width="100px">

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="AppID*">
                                <el-input style="width:300px" v-model="deploy.appId"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="App名称">
                                <el-input style="width:300px" v-model="deploy.appName"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="App类型*" label-width="100px">
                                <el-select style="width:300px" v-model="deploy.appType" placeholder="请选择">
                                    <el-option
                                            v-for="item in appTypeList"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="自动重启">
                                <el-switch v-model="deploy.keepAlive" on-color="#13ce66" off-color="#ff4949">
                                </el-switch>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="tag">
                                <el-input style="width:300px" v-model="deploy.tag"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="实例数量(个)*">
                                <el-slider style="width:300px" v-model="deploy.instances" :step="1" :min="1" :max="100" show-input></el-slider>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="包ID(数字)">
                                <el-input style="width:300px" v-model="deploy.packageId"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="包类型*" label-width="100px">
                                <el-select style="width:300px" v-model="deploy.packageType" placeholder="请选择"
                                           @change="packageChange">
                                    <el-option
                                            v-for="item in packageTypeList"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-form-item label="包地址*">
                            <el-input style="width:1090px" v-model="deploy.packageUri"></el-input>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="端口数量(个)">
                                <el-slider style="width:300px" v-model="deploy.ports" :step="1" :min="0" :max="10" show-input></el-slider>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="CPU(个)*">
                                <el-slider style="width:300px" v-model="deploy.cpus" :step="0.1" :min="0.1" :max="10" show-input></el-slider>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="内存(MB)*">
                                <el-slider style="width:300px" v-model="deploy.memory" :step="100" :min="0" :max="8000" show-input></el-slider>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="磁盘(MB)*">
                                <el-slider style="width:300px" v-model="deploy.disk" :step="100" :min="1" :max="10000" show-input></el-slider>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-form-item label="启动命令" v-if="!dockerVisible">
                            <el-input style="width:1090px" v-model="deploy.cmd"></el-input>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <el-form-item label="环境变量">
                            <el-button type="primary" size="mini" @click="addEnv">添加+</el-button>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <div v-for="(env,index) in envList">
                            <el-form-item label="环境变量" label-width="100px">
                                <el-input style="width:100px" v-model="env.key" type="text"  placeholder="key"></el-input>
                                <el-input style="width:100px" v-model="env.value" type="text"  placeholder="value"></el-input>
                                <el-button type="primary" size="mini" @click="removeEnv(index)">-</el-button>
                            </el-form-item>
                        </div>
                    </el-row>
                    <div v-if="dockerVisible">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="Network" label-width="100px">
                                    <el-select style="width:300px" v-model="deploy.networkType" placeholder="请选择">
                                        <el-option label="bridge" value="bridge"></el-option>
                                        <el-option label="host" value="host"></el-option>
                                        <el-option label="macvlan" value="macvlan"></el-option>
                                        <el-option label="none" value="none"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="privileged" label-width="100px">
                                    <el-switch
                                            v-model="deploy.privileged"
                                            on-color="#13ce66"
                                            off-color="#ff4949">
                                    </el-switch>
                                </el-form-item>
                                <el-form-item label="forcePullImage" label-width="100px">
                                    <el-switch
                                            v-model="deploy.forcePullImage"
                                            on-color="#13ce66"
                                            off-color="#ff4949">
                                    </el-switch>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="端口映射" label-width="100px">
                                    <el-button type="primary" size="mini" @click="addPortMap">添加+</el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="Volume映射" label-width="100px">
                                    <el-button type="primary" size="mini" @click="addVolume">添加+</el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <div v-for=" (port,index) in portList">
                                    <el-form-item label="端口映射" label-width="100px">
                                        <el-input style="width:100px" v-model="port.dockerPort" type="text" placeholder="dockerPort"></el-input>
                                        <el-input style="width:100px" v-model="port.hostIndex" type="text" placeholder="hostIndex"></el-input>
                                        <el-input style="width:100px" v-model="port.protocol" type="text" placeholder="protocol"></el-input>
                                        <el-button type="primary" size="mini" @click="removePortMap(index)">-</el-button>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div v-for=" (volume,index) in volumeList">
                                    <el-form-item label="Volume映射" label-width="100px">
                                        <el-input style="width:100px" v-model="volume.dockerPath" type="text" placeholder="dockerPath"></el-input>
                                        <el-input style="width:100px" v-model="volume.hostPath" type="text" placeholder="hostPath"></el-input>
                                        <el-select style="width:100px" v-model="volume.mode" placeholder="请选择">
                                            <el-option label="RW" value='RW'></el-option>
                                            <el-option label="RO" value='RO'></el-option>
                                        </el-select>
                                        <el-button type="primary" size="mini" @click="removeVolume(index)">-</el-button>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-form-item label="Parameter">
                                <el-button type="primary" size="mini" @click="addParam">添加+</el-button>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <div v-for=" (param,index) in paramList">
                                <el-form-item label="Parameter" label-width="100px">
                                    <el-input style="width:100px" v-model="param.key" type="text"></el-input>
                                    <el-input style="width:100px" v-model="param.value" type="text"></el-input>
                                    <el-button type="primary" size="mini" @click="removeParam(index)">-</el-button>
                                </el-form-item>
                            </div>
                        </el-row>
                    </div>
                </el-form>
            </div>

            <div align="center">
                <el-row>
                    <el-col :span="0">
                        <el-button type="primary" @click="addDeploy">提交</el-button>
                        <el-button @click="onClose">取消</el-button>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>

    import {mapGetters, mapActions} from 'vuex';

    export default{

        methods: {
            addDeploy(){
                this.deploy.envMap = {};
                this.deploy.dockerParametes = {};
                this.deploy.portMappings = [];
                this.deploy.volumes = [];

                for (var env in this.envList) {
                    this.deploy.envMap[this.envList[env].key] = this.envList[env].value;
                }

                if (this.deploy.packageType == 'image') {

                    for (var port in this.portList) {
                        this.deploy.portMappings.push({
                            containerPort:this.portList[port].dockerPort,
                            hostPortIndex:this.portList[port].hostIndex,
                            protocol:this.portList[port].protocol
                        })
                    }

                    for(var volume in this.volumeList){
                        this.deploy.volumes.push({
                            containerPath:this.volumeList[volume].dockerPath,
                            hostPath:this.volumeList[volume].hostPath,
                            mode:this.volumeList[volume].mode
                        })
                    }

                    for (var param in this.paramList){
                        this.deploy.dockerParametes[this.paramList[param].key] = this.paramList[param].value;
                    }
                }

                this.$store.dispatch('createDeploy', this.deploy);
            },

            addPortMap(){
                this.portList.push({
                })
            },

            addVolume(){
                this.volumeList.push({
                })
            },

            addEnv(){
                this.envList.push({
                    key: '',
                    value: ''
                })
            },

            removeEnv(index){
                this.envList.splice(index, 1);
            },

            removeVolume(index){
                this.volumeList.splice(index, 1);
            },

            removePortMap(index){
                this.portList.splice(index, 1);
            },

            addParam(){
                this.paramList.push({
                    key: '',
                    value: ''
                })
            },
            removeParam(index){
                this.paramList.splice(index, 1);
            },
            onClose(){
                this.$router.push('/listdeploy');
            },

            packageChange(data){
                if (data === 'image') {
                    this.dockerVisible = true;
                    this.deployLabel = '镜像地址';
                    this.portList = [];
                    this.volumeList = [];
                } else {
                    this.deployLabel = '包地址';
                    this.dockerVisible = false;
                }
            }
        },
        data: function () {
            return {
                deployLabel:'包地址',
                portList: [],
                volumeList: [],
                envList: [],
                paramList: [],
                dockerVisible: false,
                deploy: {
                    appId: '',
                    appName: '',
                    appType: 'service',
                    packageType: '',
                    packageId: '',
                    packageUri: '',
                    cpus: 0.1,
                    memory:100,
                    disk:100,
                    instances: 0,
                    ports: 0,
                    tag: '',
                    cmd: '',
                    keepAlive: true,
                    envMap: {},
                    dockerParametes: {},
                    networkType: '',
                    portMappings: [],
                    privileged: false,
                    forcePullImage: false,
                    volumes: [],
                    createTime: '',
                }
            }
        },
        computed: {
            ...mapGetters({
                appTypeList: 'getAppTypeList',
                packageTypeList: 'getPackageTypeList',
                promptMessage: 'getPromptMessage'
            })
        },
        created (){
        },
        watch: {
            promptMessage: function (newMessage) {
               if (newMessage.success) {
                   this.$message.success("操作成功");
               } else {
                   this.$message.error(newMessage.message);
               }
            }
        }
    }

</script>

