<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">IPAM管理</el-breadcrumb-item>
                    <el-breadcrumb-item>子网列表</el-breadcrumb-item>
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
            <el-table :data="subnetList" style="..." empty-text="暂无数据" border fit>
                <el-table-column label="ID" prop="id" align="center" sortable></el-table-column>
                <el-table-column label="host" prop="host" align="center"></el-table-column>
                <el-table-column label="子网" prop="subnet" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" width="220"></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" width="220"></el-table-column>
                <el-table-column v-if="isLogin"  label="操作" align="center" width="200">
                    <template scope="scope">
                        <el-button @click="handleDelete(scope)" size="small" type="warning">删除</el-button>
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

        <el-dialog title="添加子网" :visible.sync="dialogVisible_addSubnet" size="tiny">
            <el-form ref="form" :model="subnet" label-width="100px">
                <el-form-item label="Host*">
                    <el-input style="width:350px" v-model="subnet.host"></el-input>
                </el-form-item>
                <el-form-item label="子网*">
                    <el-input style="width:350px" v-model="subnet.subnet"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addSubnet">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否删除该子网！" :visible.sync="dialogVisible_deleteSubnet" size="tiny">
            <el-form ref="form" :model="subnet" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="subnet.id"></el-input>
                </el-form-item>
                <el-form-item label="host">
                    <el-input :disabled="true" style="width:350px" v-model="subnet.host"></el-input>
                </el-form-item>
                <el-form-item label="子网">
                    <el-input :disabled="true" style="width:350px" v-model="subnet.subnet"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="deleteSubnet">确认</el-button>
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
                this.$store.dispatch('querySubnet', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange(){
                this.$store.dispatch('querySubnet', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            onClose(){
                this.dialogVisible_addSubnet = false;
                this.dialogVisible_deleteSubnet = false;
            },
            onSearch(){
                this.$store.dispatch('querySubnet', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handAdd(){
                this.subnet = {
                    host: '',
                    subnet: ''
                };
                this.dialogVisible_addSubnet = true;
            },
            handleDelete(data){
                this.subnet = data.row;
                this.dialogVisible_deleteSubnet = true;
            },
            addSubnet(){
                this.dialogVisible_addSubnet = false;
                this.$store.dispatch('addSubnet', this.subnet);
            },
            deleteSubnet(){
                this.dialogVisible_deleteSubnet = false;
                this.$store.dispatch('deleteSubnet', {
                    id: this.subnet.id,
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
                dialogVisible_addSubnet: false,
                dialogVisible_deleteSubnet: false,
                currentField: {
                    value: 'subnet',
                    label: '子网'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                subnet: {
                    id: 0,
                    host: '',
                    subnet: '',
                }
            }
        },
        computed: {
            ...mapGetters({
                subnetList: 'getSubnetList',
                fieldList: 'getSubnetFieldList',
                total: 'getSubnetTotal',
                isLogin: 'getLoginState',
                promptMessage: 'getPromptMessage'
            })
        },
        created (){
            this.$store.dispatch('querySubnet');
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

