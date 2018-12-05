<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">事件管理</el-breadcrumb-item>
                    <el-breadcrumb-item>游标列表</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>

        <br/>

        <div>
            <el-table :data="offsetList" style="..." empty-text="暂无数据" border fit>
                <el-table-column label="ID" prop="id" align="center" sortable></el-table-column>
                <el-table-column label="Offset" prop="offset" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" width="220"></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" width="220"></el-table-column>

                <el-table-column  v-if="isLogin" label="操作" align="center" width="200">
                    <template scope="props">
                        <el-button @click="handleDelete(props)" size="mini" type="warning">删除</el-button>
                        <el-button @click="handleReset(props)" size="mini" type="danger">重置</el-button>
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

        <el-dialog title="请确认是否删除该游标！" :visible.sync="dialogVisible_deleteOffset" size="tiny" >
            <el-form ref="form" :model="offset" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="offset.id"></el-input>
                </el-form-item>
                <el-form-item label="Offset">
                    <el-input :disabled="true" style="width:350px" v-model="offset.offset"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="deleteOffset">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>


        <el-dialog title="重置Offset" :visible.sync="dialogVisible_resetOffset" size="tiny" >
            <el-form ref="form" :model="offset" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="offset.id"></el-input>
                </el-form-item>
                <el-form-item label="Host">
                    <el-input  style="width:350px" v-model="offset.offset"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="resetOffset">确认</el-button>
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
                this.$store.dispatch('queryOffset', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },
            handleCurrentChange(){
                this.$store.dispatch('queryOffset', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            onClose(){
                this.dialogVisible_deleteOffset = false;
                this.dialogVisible_resetOffset = false;
            },
            onSearch(){
                this.$store.dispatch('queryOffset', {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    field: this.currentField,
                    keyword: this.currentValue
                });
            },

            handleDelete(data){
                this.offset = data.row;
                this.dialogVisible_deleteOffset = true;
                this.dialogVisible_resetOffset= false;
            },

            deleteOffset(){
                this.dialogVisible_deleteOffset = false;
                this.$store.dispatch('deleteOffset', {
                    id: this.offset.id,
                });
            },

            handleReset(data){
                this.offset = data.row;
                this.dialogVisible_deleteOffset = false;
                this.dialogVisible_resetOffset= true;
            },
            resetOffset(){
                this.dialogVisible_resetOffset = false;
                this.$store.dispatch('resetOffset',{
                    id:this.offset.id,
                    offset:this.offset.offset
                });
            },

        },

        data: function () {
            return {
                dialogVisible_deleteOffset: false,
                dialogVisible_resetOffset : false,

                currentField: {
                    value: 'id',
                    label: 'ID'
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: null,
                offset: {
                    id: 0,
                    offset: 0,
                }
            }
        },
        computed: {
            ...mapGetters({
                offsetList: 'getOffsetList',
                total: 'getOffsetTotal',
                promptMessage: 'getPromptMessage',
                isLogin: 'getLoginState',
            })
        },
        created (){
            this.$store.dispatch('listOffset');
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

