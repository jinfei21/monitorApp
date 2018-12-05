<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">Cleanup管理</el-breadcrumb-item>
                    <el-breadcrumb-item>Cleanup列表</el-breadcrumb-item>
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
                <el-col :span="2" :offset="1">
                    <el-button type="primary" @click="onSearch">搜索</el-button>
                </el-col>
                <el-button type="primary" fixed="right" @click="handleAdd" class="add_button">添加</el-button>
            </el-row>
        </div>
        <br/>

        <div>
            <el-table :data="cleanupList" style="width: 100%" border fit>
                <el-table-column label="ID" prop="id" align="center" sortable width="120"></el-table-column>
                <el-table-column label="AppID" prop="appId" align="center" width="180"></el-table-column>
                <el-table-column label="DeployID" prop="deployId" align="center" width="180"></el-table-column>
                <el-table-column label="类型" prop="type" align="center" ></el-table-column>
                <el-table-column label="执行参数" prop="data" align="center" ></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center" ></el-table-column>
                <el-table-column label="修改时间" prop="modifyTime" align="center" ></el-table-column>

                <el-table-column   v-if="isLogin" label="操作" align="center" width="120">
                    <template scope="scope">
                        <el-button @click="handleDelete(scope)" size="small" type="danger">删除</el-button>
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

        <el-dialog title="创建Cleanup" :visible.sync="dialogVisible_addCleanup" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="cleanup" label-width="100px">
                <el-form-item label="AppID">
                    <el-input style="width:350px" v-model="cleanup.appId"></el-input>
                </el-form-item>
                <el-form-item label="DeployID">
                    <el-input style="width:350px" v-model="cleanup.deployId"></el-input>
                </el-form-item>
                <el-form-item label="Type">
                    <el-input style="width:350px" v-model="cleanup.type"></el-input>
                </el-form-item>
                <el-form-item label="Data">
                    <el-input style="width:350px" v-model="cleanup.data"></el-input>
                </el-form-item>
                <el-button type="primary" @click="addCleanup">提交</el-button>
                <el-button @click="onClose">取消</el-button>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否删除该Cleanup!" :visible.sync="dialogVisible_deleteCleanup" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="cleanup" label-width="100px">
                <el-form-item label="AppID">
                    <el-input :disabled="true" style="width:350px" v-model="cleanup.appId"></el-input>
                </el-form-item>
                <el-form-item label="DeployID">
                    <el-input :disabled="true" style="width:350px" v-model="cleanup.deployId"></el-input>
                </el-form-item>
                <el-form-item label="Type">
                    <el-input :disabled="true" style="width:350px" v-model="cleanup.type"></el-input>
                </el-form-item>
                <el-form-item label="Data">
                    <el-input :disabled="true" style="width:350px" v-model="cleanup.data"></el-input>
                </el-form-item>
                <el-button type="primary" @click="deleteCleanup">确认</el-button>
                <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';

    export default {

    	methods:{
    	     handleView() {
		     },
		     handleEdit(data){
		      	this.spec = data.row;
		      	this.dialogVisible = true;
		     },
		     handleSizeChange(val){
		     	this.pageSize = val;
		        this.currentPage= 1;
		      	this.$store.dispatch('queryCleanup',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue
		      	});
		     },
		     handleCurrentChange(){
		      	this.$store.dispatch('queryCleanup',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue
		      	});
		     },
             handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {
                    });
             },
		     handleAdd(){
		      	this.cleanup={
			          appId:'',
			          deployId:'',
			          type:'',
			          data:'',
			        };
		      	this.dialogVisible_addCleanup = true;
		     },
		     handleDelete(data){
                this.cleanup = data.row;
		        this.dialogVisible_deleteCleanup = true;
		     },
		     deleteCleanup(){
		        this.dialogVisible_deleteCleanup = false;
		      	this.$store.dispatch('deleteCleanup',{
                    id: this.cleanup.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },
		     addCleanup(){
		      	this.dialogVisible_addCleanup= false;
		      	this.$store.dispatch('addCleanup',this.cleanup);
		     },
		     onClose(){
		     	this.dialogVisible_addCleanup= false;
		     	this.dialogVisible_deleteCleanup= false;
		     },
		     onSearch(){
		      	this.$store.dispatch('queryCleanup',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue,
		      	});
		    }
    	},

        data: function(){
            return {
			        dialogVisible:false,
			        dialogVisible_addCleanup:false,
			        dialogVisible_deleteCleanup:false,
			        currentField:{
			          value: 'appId',
			          label: 'AppId'
			        },
			        currentPage:1,
			        pageSize:10,
			        currentValue:'',
			        cleanup:{
			          appId:'',
			          deployId:'',
			          type:'',
			          data:'',
			          createTime:'',
			        }
            }
        },
        computed:{
        	...mapGetters({
        		cleanupList: 'getCleanupList',
        		fieldList: 'getCleanupFieldList',
        		total: 'getCleanupTotal',
                isLogin: 'getLoginState',
        		promptMessage: 'getPromptMessage'
        	})
        },
        created (){
        	this.$store.dispatch('queryCleanup');
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

