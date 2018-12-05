<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">IPAM管理</el-breadcrumb-item>
                    <el-breadcrumb-item>IP列表</el-breadcrumb-item>
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
                <el-col :span="5" :offset="1">
                    <el-input v-model="currentValue" placeholder="请输入内容" @change="onSearch"></el-input>
                </el-col>
                <el-col :span="2" :offset="1">
                    <el-button type="primary" fixed="middle" @click="onSearch">搜索</el-button>
                </el-col>
                <el-button type="primary" fixed="right" @click="handAdd" class="add_button">添加</el-button>
            </el-row>
        </div>
        <br/>

        <div>
            <el-table :data="ipList" style="..." empty-text="暂无数据" border fit>
                <el-table-column label="ID" prop="id" align="center" sortable></el-table-column>
                <el-table-column label="HOST" prop="host" align="center"></el-table-column>
                <el-table-column label="IP" prop="ip" align="center"></el-table-column>
                <el-table-column label="状态" prop="state" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" width="220"></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" width="220"></el-table-column>

                <el-table-column v-if="isLogin" label="操作" align="center" width="200">
                    <template scope="props">
                        <el-button @click="handleDelete(props)" size="mini" type="warning">删除</el-button>
                        <el-button v-if="props.row.state == 'FREE'" @click="handleDisable(props)" size="mini"
                                   type="danger">停止
                        </el-button>
                        <el-button v-if="props.row.state == 'DISUSE'" @click="handleEnable(props)" size="mini"
                                   type="danger">激活
                        </el-button>

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

        <el-dialog title="添加IP" :visible.sync="dialogVisible_addIP" size="tiny" >
            <el-form ref="form" :model="ip" label-width="100px">
                <el-form-item label="Host*">
                    <el-input style="width:350px" v-model="ip.host"></el-input>
                </el-form-item>
                <el-form-item label="IP">
                    <el-input style="width:350px" v-model="ip.ip" ></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select style="width:300px" v-model="ip.state" placeholder="请选择">
                        <el-option label="可用" value="free"></el-option>
                        <el-option label="不可用" value="disuse"></el-option>
                        <el-option label="占用中" value="used"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="addIP">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否删除该IP！" :visible.sync="dialogVisible_deleteIP" size="tiny" >
            <el-form ref="form" :model="ip" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="ip.id"></el-input>
                </el-form-item>
                <el-form-item label="Host">
                    <el-input :disabled="true" style="width:350px" v-model="ip.host"></el-input>
                </el-form-item>
                <el-form-item label="IP">
                    <el-input :disabled="true" style="width:350px" v-model="ip.ip"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="deleteIP">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否下线IP！" :visible.sync="dialogVisible_disableIP" size="tiny" >
            <el-form ref="form" :model="ip" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="ip.id"></el-input>
                </el-form-item>
                <el-form-item label="Host">
                    <el-input :disabled="true" style="width:350px" v-model="ip.host"></el-input>
                </el-form-item>
                <el-form-item label="IP">
                    <el-input :disabled="true" style="width:350px" v-model="ip.ip"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="disableIP">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否激活IP！" :visible.sync="dialogVisible_enableIP" size="tiny" >
            <el-form ref="form" :model="ip" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="ip.id"></el-input>
                </el-form-item>
                <el-form-item label="Host">
                    <el-input :disabled="true" style="width:350px" v-model="ip.host"></el-input>
                </el-form-item>
                <el-form-item label="IP">
                    <el-input :disabled="true" style="width:350px" v-model="ip.ip"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="enableIP">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';

    export default {

        methods: {

            handleSizeChange(val){
                this.pageSize = val;
                this.currentPage = 1;
                this.$store.dispatch('queryIP', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange(){
                this.$store.dispatch('queryIP', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            onClose(){
                this.dialogVisible_addIP = false;
                this.dialogVisible_deleteIP = false;
                this.dialogVisible_disableIP = false;
                this.dialogVisible_enableIP = false;
            },
            onSearch(){
                this.$store.dispatch('queryIP', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handAdd(){
                this.ip = {
                    host: '',
                    ip: ''
                };
                this.dialogVisible_addIP = true;
                this.dialogVisible_deleteIP = false;
                this.dialogVisible_disableIP = false;
                this.dialogVisible_enableIP = false;
            },
            handleDelete(data){
                this.ip = data.row;
                this.dialogVisible_addIP = false;
                this.dialogVisible_deleteIP = true;
                this.dialogVisible_disableIP = false;
                this.dialogVisible_enableIP = false;
            },
            handleDisable(data){
                this.ip = data.row;
                this.dialogVisible_addIP = false;
                this.dialogVisible_deleteIP = false;
                this.dialogVisible_disableIP = true;
                this.dialogVisible_enableIP = false;
            },
            handleEnable(data){
                this.ip = data.row;
                this.dialogVisible_addIP = false;
                this.dialogVisible_deleteIP = false;
                this.dialogVisible_disableIP = false;
                this.dialogVisible_enableIP = true;
            },
            addIP(){
                this.dialogVisible_addIP = false;
                this.$store.dispatch('addIP', this.ip);
            },
            deleteIP(){
                this.dialogVisible_deleteIP = false;
                this.$store.dispatch('deleteIP', {
                    id: this.ip.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
            },
            disableIP(){
                this.dialogVisible_disableIP = false;
                this.$store.dispatch('disableIP', {
                    ip: this.ip.ip,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
            },
            enableIP(){
                this.dialogVisible_enableIP = false;
                this.$store.dispatch('enableIP', {
                    ip: this.ip.ip,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
            }
        },

        data: function () {
            return {
                dialogVisible_addIP: false,
                dialogVisible_deleteIP: false,
                dialogVisible_disableIP : false,
                dialogVisible_enableIP : false,
                currentField: {
                    value: 'name',
                    label: '规格名'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                ip: {
                    id: 0,
                    host: '',
                    ip: '',
                    status: ''
                }
            }
        },
        computed: {
            ...mapGetters({
                ipList: 'getIPList',
                fieldList: 'getIPFieldList',
                total: 'getIPTotal',
                isLogin: 'getLoginState',
                promptMessage: 'getPromptMessage'
            })
        },
        created (){
            this.$store.dispatch('queryIP');
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

