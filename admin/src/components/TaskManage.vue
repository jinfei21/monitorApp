<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">Task管理</el-breadcrumb-item>
                    <el-breadcrumb-item>Task列表</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>
        <div>
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
                <el-col :span="3" :offset="1">
                    <el-button type="primary" fixed="right" @click="onSearch">搜索</el-button>
                </el-col>
            </el-row>
        </div>
        <br/>

        <div>
            <el-table :data="taskList" style="width: 100%" empty-text="暂无数据" border fit>
                <el-table-column type="expand">
                    <template scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="SlaveID：">
                                <span>{{ props.row.slaveId }}</span>
                            </el-form-item>
                            <el-form-item label="实例号：">
                                <span>{{ props.row.instanceNo }}</span>
                            </el-form-item>
                            <el-form-item label="Host：">
                                <span>{{ props.row.host }}</span>
                            </el-form-item>
                            <el-form-item label="创建时间：">
                                <span>{{ props.row.createTime }}</span>
                            </el-form-item>
                            <el-form-item label="Ports：">
                                <span>{{ props.row.ports }}</span>
                            </el-form-item>
                            <el-form-item label="Sandbox：">
                                <span>{{ props.row.sandbox }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="ID" prop="id" align="center" width="110" sortable >
                    <template scope="props">
                        <router-link :to="{name: 'taskinfo', params: { id: props.row.id }}">{{props.row.id}}</router-link>
                    </template>
                </el-table-column>
                <el-table-column label="DeployID" prop="deployId" align="center" width="150" ></el-table-column>
                <el-table-column label="AppID" prop="appId" align="center" width="160" ></el-table-column>
                <el-table-column label="IP" prop="ip" align="center" width="170" ></el-table-column>
                <el-table-column label="端口" prop="port" align="center" width="140"></el-table-column>
                <el-table-column label="状态" prop="status" align="center" ></el-table-column>
                <el-table-column label="调度时间" prop="scheduleTime" align="center" width="210"></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" width="210"></el-table-column>

                <el-table-column v-if="isLogin" label="操作" align="center" width="170" >
                    <template scope="scope">
                        <label v-if="scope.row.status != 'TASK_RUNNING'">
                            <el-button type="text" >暂不可操作</el-button>
                        </label>
                        <label v-if="scope.row.status == 'TASK_RUNNING'">
                            <el-button @click="handleStop(scope)" size="small" type="danger">停止</el-button>
                        </label>
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

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                size="tiny">
            <span>请确认是否停止该任务！</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onSubmit">确认</el-button>
                <el-button @click="onClose">取消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';

    export default {

        methods: {
            handleStop(data) {
                console.log(data);
                this.task = data.row;
                this.dialogVisible = true;
            },

            handleSizeChange(val) {
                this.pageSize = val;
                this.currentPage = 1;
                this.$store.dispatch('queryTask', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange() {
                this.$store.dispatch('queryTask', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            onSubmit() {
                this.dialogVisible = false;
                this.$store.dispatch('stopTask', {
                    taskId: this.task.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
                console.log(this.task)
            },
            onClose() {
                this.dialogVisible = false;
            },

            onSearch() {
                console.log(this.currentValue);
                this.currentPage = 1;
                this.$store.dispatch('queryTask', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            }
        },

        data: function () {
            return {
                dialogVisible: false,
                currentField: {
                    value: 'appId',
                    label: 'AppID'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                task: {
                    deployId: 0,
                    appId: '',
                    status: '',
                    host: '',
                    ip:'',
                    port: 0,
                    slaveId: '',
                    instanceNo: 0,
                    scheduleTime: '',
                    modifyTime: ''
                }
            }
        },

        computed: {
            ...mapGetters({
                taskList: 'getTaskList',
                fieldList: 'getTaskFieldList',
                total: 'getTaskTotal',
                isLogin: 'getLoginState',
            })
        },

        created() {
            var payload = null
            if(this.$route.query.deployId != undefined){
                payload = {
                    field: 'deployId',
                    keyword: this.$route.query.deployId,
                    page:1,
                    pageSize:100};
            }
            this.$store.dispatch('queryTask',payload);
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
</style>

