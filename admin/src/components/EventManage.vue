<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">事件管理</el-breadcrumb-item>
                    <el-breadcrumb-item>事件列表</el-breadcrumb-item>
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
            </el-row>
        </div>
        <br/>

        <div>
            <el-table :data="eventList" style="..." empty-text="暂无数据" border fit>
                <el-table-column label="ID" prop="id" align="center" sortable></el-table-column>
                <el-table-column label="DeployID" prop="deployId" align="center"></el-table-column>
                <el-table-column label="类型" prop="type" align="center"></el-table-column>
                <el-table-column label="data" prop="data" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" width="220"></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" width="220"></el-table-column>
                <el-table-column  v-if="isLogin" label="操作" align="center" width="200">
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


        <el-dialog title="请确认是否删除该事件！" :visible.sync="dialogVisible_deleteEvent" size="tiny">
            <el-form ref="form" :model="event" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="event.id"></el-input>
                </el-form-item>
                <el-form-item label="DeployID">
                    <el-input :disabled="true" style="width:350px" v-model="event.deployId"></el-input>
                </el-form-item>
                <el-form-item label="数据">
                    <el-input :disabled="true" style="width:350px" v-model="event.data"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="deleteEvent">确认</el-button>
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
                this.$store.dispatch('queryEvent', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange(){
                this.$store.dispatch('queryEvent', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            onClose(){
                this.dialogVisible_deleteEvent = false;
            },
            onSearch(){
                this.$store.dispatch('queryEvent', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            handleDelete(data){
                this.event = data.row;
                this.dialogVisible_deleteEvent = true;
            },

            deleteEvent(){
                this.dialogVisible_deleteEvent = false;
                this.$store.dispatch('deleteEvent', {
                    id: this.event.id,
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
                dialogVisible_deleteEvent: false,
                currentField: {
                    value: 'data',
                    label: 'data'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                event: {
                    id: 0,
                    deployId: '',
                    data: '',
                }
            }
        },
        computed: {
            ...mapGetters({
                eventList: 'getEventList',
                fieldList: 'getEventFieldList',
                total: 'getEventTotal',
                isLogin: 'getLoginState',
                promptMessage: 'getPromptMessage'
            })
        },
        created (){
            this.$store.dispatch('queryEvent');
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

