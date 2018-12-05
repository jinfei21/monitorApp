<template>
    <div>
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{path:'/home'}">Deploy管理</el-breadcrumb-item>
                        <el-breadcrumb-item>Deploy列表</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <el-row class="query-form">
                <el-col :span="3">
                    <el-select v-model="currentField" placeholder="请选择">
                        <el-option
                                v-for="item in fieldList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-input v-model="currentValue" placeholder="请输入内容" @change="onSearch"></el-input>
                </el-col>
                <el-col :span="2" :offset="1">
                    <el-button type="primary" @click="onSearch">搜索</el-button>
                </el-col>
                <el-button type="primary" fixed="right" @click="addDeploy" class="add_button">添加Deploy</el-button>
            </el-row>
        </div>
        <br/>

        <div class="tdaction">
            <el-table :data="deployList" style="..." border fit >
                <el-table-column type="expand">
                    <template scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="包ID">
                                <span>{{ props.row.packageId }}</span>
                            </el-form-item>
                            <el-form-item label="包地址">
                                <span>{{ props.row.packageUri }}</span>
                            </el-form-item>
                            <el-form-item label="包类型">
                                <span>{{ props.row.packageType }}</span>
                            </el-form-item>
                            <el-form-item label="CPU">
                                <span>{{ props.row.cpus }}</span>
                            </el-form-item>
                            <el-form-item label="内存">
                                <span>{{ props.row.memory }}</span>
                            </el-form-item>
                            <el-form-item label="磁盘">
                                <span>{{ props.row.disk }}</span>
                            </el-form-item>
                            <el-form-item label="实例数量">
                                <span>{{ props.row.instances }}</span>
                            </el-form-item>
                            <el-form-item label="端口数">
                                <span>{{ props.row.ports }}</span>
                            </el-form-item>
                            <el-form-item label="启动命令">
                                <span>{{ props.row.cmd }}</span>
                            </el-form-item>
                            <el-form-item label="自动重启">
                                <span>{{ props.row.keepAlive }}</span>
                            </el-form-item>
                            <el-form-item label="环境变量">
                                <span>{{ props.row.envMap }}</span>
                            </el-form-item>
                            <el-form-item label="dockerInfo">
                                <span>{{ props.row.dockerInfo }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="ID" prop="id" align="center" sortable >
                    <template scope="props">
                        <router-link :to="{name: 'taskmanage', query: { deployId: props.row.id }}">{{props.row.id}}</router-link>
                    </template>
                </el-table-column>
                <el-table-column label="AppID" prop="appId" align="center"></el-table-column>
                <el-table-column label="App名称" prop="appName" align="center"></el-table-column>
                <el-table-column label="App类型" prop="appType" align="center"></el-table-column>
                <el-table-column label="标签" prop="tag" align="center"></el-table-column>
                <el-table-column label="状态" prop="state" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" ></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" ></el-table-column>

                <el-table-column  v-if="isLogin"  label="操作" align="center" width="100px" >
                    <template scope="props">                     
                       <el-button v-if="props.row.state == 'ACTIVE'" @click="handleStop(props.row)" size="mini" type="danger">停止</el-button>                
                       <el-button v-if="props.row.state == 'SCHEDULE'" @click="handlePause(props.row)" size="mini" type="warning">暂停</el-button>                
                       <el-button v-if="props.row.state == 'SCHEDULE'" @click="handleResize(props.row)" size="mini" type="warning">伸缩</el-button>
                       <el-button v-if="props.row.state == 'FINISHED'" @click="handleStop(props.row)" size="mini" type="danger">停止</el-button>
                       <el-button v-if="props.row.state == 'FINISHED'" @click="handleResize(props.row)" size="mini" type="warning">伸缩</el-button>
                       <el-button v-if="props.row.state == 'DOWN'" @click="handleActive(props.row)" size="mini" type="success">激活</el-button>
                       <el-button v-if="props.row.state == 'OVERDUE'" @click="handleStop(props.row)" size="mini" type="danger">停止</el-button>
                       <el-button v-if="props.row.state == 'OVERDUE'" @click="handleActive(props.row)" size="mini" type="success">激活</el-button>
                       <el-button v-if="props.row.state == 'PAUSED'" @click="handleStop(props.row)" size="mini" type="danger">停止</el-button>
                       <el-button v-if="props.row.state == 'PAUSED'" @click="handleActive(props.row)" size="mini" type="success">激活</el-button>
                       <el-button v-if="props.row.state == 'COOLDOWN'" @click="handleStop(props.row)" size="mini" type="danger">停止</el-button>
                       <el-button v-if="props.row.state == 'COOLDOWN'" @click="handleActive(props.row)" size="mini" type="success">激活</el-button>
                       <el-button v-if="props.row.state == 'FAIL'" @click="handleActive(props.row)" size="mini" type="success">激活</el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div align='center' style="margin-top: 10px">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="激活Deploy" :visible.sync="dialogVisible_active" :before-close="handleClose" size="tiny">
            <el-form ref="form" :model="deploy" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.id"></el-input>
                </el-form-item>
                <el-form-item label="AppID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appId"></el-input>
                </el-form-item>
                <el-form-item label="App名称">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="activeDeploy">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="暂停Deploy" :visible.sync="dialogVisible_pause" :before-close="handleClose" size="tiny">
            <el-form ref="form" :model="deploy" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.id"></el-input>
                </el-form-item>
                <el-form-item label="AppID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appId"></el-input>
                </el-form-item>
                <el-form-item label="App名称">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="pauseDeploy">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="停止Deploy" :visible.sync="dialogVisible_stop" :before-close="handleClose" size="tiny">
            <el-form ref="form" :model="deploy" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.id"></el-input>
                </el-form-item>
                <el-form-item label="AppID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appId"></el-input>
                </el-form-item>
                <el-form-item label="App名称">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="stopDeploy">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="伸缩Deploy" :visible.sync="dialogVisible_resize" :before-close="handleClose" size="tiny">
            <el-form ref="form" :model="deploy" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.id"></el-input>
                </el-form-item>
                <el-form-item label="AppID">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appId"></el-input>
                </el-form-item>
                <el-form-item label="App名称">
                    <el-input :disabled="true" style="width:300px" v-model="deploy.appName"></el-input>
                </el-form-item>
                <el-form-item label="instance数量(个)">
                    <el-slider style="width:300px" v-model="deploy.instances" :step="1" :min="1" :max="100" show-input></el-slider>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="resizeDeploy">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';

    export default{
        methods: {
            onclick(cellValue){
            },

            addDeploy(){
                this.$router.push('/adddeploy');
            },

            handleStop(data){
                this.deploy = data;
                this.dialogVisible_stop = true;
            },

            handleActive(data){
                this.deploy = data;
                this.dialogVisible_active = true;
            },

            handlePause(data){
                this.deploy = data;
                this.dialogVisible_pause = true;
            },

            handleResize(data){
                this.deploy = data;
                this.dialogVisible_resize = true;
            },
            onClose(){
                this.dialogVisible_active = false;
                this.dialogVisible_stop = false;
                this.dialogVisible_pause = false;
                this.dialogVisible_resize = false;
            },

            handleClose(done) {
                done();
            },
            onSearch(){
                this.$store.dispatch('queryDeploy', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleSizeChange(data) {
                this.pageSize = data;
                this.$store.dispatch('queryDeploy', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange(data) {
                this.currentPage = data;
                this.$store.dispatch('queryDeploy', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            resizeDeploy(){
                this.$store.dispatch('resizeDeploy', {
                    deployId: this.deploy.id,
                    instances: this.deploy.instances,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
                this.dialogVisible_resize = false;
            },
            stopDeploy(){
                this.$store.dispatch('stopDeploy', {
                    deployId: this.deploy.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
                this.dialogVisible_stop = false;
            },
            pauseDeploy(){
                this.$store.dispatch('pauseDeploy', {
                    deployId: this.deploy.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
                this.dialogVisible_pause = false;
            },
            activeDeploy(){
                this.$store.dispatch('activeDeploy', {
                    deployId: this.deploy.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
                this.dialogVisible_active = false;
            }
        },

        data: function () {
            return {
                dialogVisible_active: false,
                dialogVisible_pause: false,
                dialogVisible_stop: false,
                dialogVisible_resize: false,
                currentField: {
                    value: 'ID',
                    label: 'Deploy编号'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                deploy: {
                    id: 0,
                    appId: '',
                    appName: '',
                    appType: '',
                    packageType: '',
                    packageId: '',
                    packageUri: '',
                    specName: '',
                    instances: 0,
                    ports: 0,
                    tag: '',
                    cmd: '',
                    keepAlive: true,
                    envMap: '',
                    dockerParametes: '',
                    networkType: '',
                    portMappings: '',
                    privileged: '',
                    forcePullImage: '',
                    volumes: '',
                    createTime: '',
                }
            }
        },
        computed: {
            ...mapGetters({
                fieldList: 'getDeployFieldList',
                deployList: 'getDeployList',
                total: 'getDeployTotal',
                promptMessage: 'getPromptMessage',
                isLogin: 'getLoginState',

            })
        },
        created (){
            this.$store.dispatch('queryDeploy');
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

<style>
    .add_button {
        float: right;
    }

    .demo-table-expand {
        font-size: 0;
        padding: 20px 0;
    }

    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .tdaction td{
      padding-left:0!important;
      padding-right:0!important;
    }
    .tdaction  td .cell{
      padding-left:0!important;
      padding-right:0!important;
    }
</style>