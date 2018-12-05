<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">集群管理</el-breadcrumb-item>
                    <el-breadcrumb-item>Rack列表</el-breadcrumb-item>
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
                <el-col :span="2"  :offset="1">
                    <el-button type="primary" @click="onSearch">搜索</el-button>
                </el-col>
                <el-button type="primary" fixed="right" @click="handleAdd" class="add_button">添加</el-button>
            </el-row>
        </div>
        <br/>

        <div>
            <el-table :data="rackList" style="width: 100%" border fit>
                <template scope="scope">

                    <el-table-column label="ID" prop="id" align="center" sortable width="120"></el-table-column>
                    <el-table-column label="RackID" prop="rackId" align="center" ></el-table-column>
                    <el-table-column label="状态" prop="status" align="center" ></el-table-column>
                    <el-table-column label="创建时间" prop="createTime" align="center" width="220"></el-table-column>
                    <el-table-column label="修改时间" prop="modifyTime" align="center" width="220"></el-table-column>

                    <el-table-column v-if="isLogin" label="操作" align="center" width="200">
                        <template scope="scope">
                            <label v-if="scope.row.status == 'RACK_DECOMISSION'">
                                <el-button @click="handleUp(scope)" size="small" type="success">上线</el-button>
                            </label>
                            <label v-if="scope.row.status == 'UP'">
                                <el-button @click="handleDown(scope)"  size="small" type="danger">下线</el-button>
                            </label>
                        </template>
                    </el-table-column>

                </template>
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

        <el-dialog title="Rack上线" :visible.sync="dialogVisible_rackUp" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="rack" label-width="100px" >
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="rack.id"></el-input>
                </el-form-item>
                <el-form-item label="RackID">
                    <el-input :disabled="true" style="width:350px" v-model="rack.rackId"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="upRack">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否下线该Rack!" :visible.sync="dialogVisible_rackDown" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="rack" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="rack.id"></el-input>
                </el-form-item>
                <el-form-item label="RackID">
                    <el-input :disabled="true" style="width:350px" v-model="rack.rackId"></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="downRack">确认</el-button>
                <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="创建Rack" :visible.sync="dialogVisible_addRack" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="rack" label-width="100px">
                <el-form-item label="RackID*">
                    <el-input style="width:350px" v-model="rack.rackId"></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="addRack">提交</el-button>
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
		      	this.$store.dispatch('queryRack',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue
		      	});
		     },
		     handleCurrentChange(){
		      	this.$store.dispatch('queryRack',{
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
             handleUp(data){
                this.rack = data.row;
		        this.dialogVisible_rackUp = true;
		     },
		     handleDown(data){
                this.rack = data.row;
		        this.dialogVisible_rackDown = true;
		     },
		     handleAdd(){
		      	this.rack={
			          rackId:null,
			        };
		      	this.dialogVisible_addRack = true;
		     },
		     upRack(){
		        this.dialogVisible_rackUp= false;
		      	this.$store.dispatch('upRack',{
                    id: this.rack.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },
		     downRack(){
		        this.dialogVisible_rackDown= false;
		      	this.$store.dispatch('downRack',{
                    id: this.rack.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },
		     addRack(){
		     	this.dialogVisible_addRack= false;
		      	this.$store.dispatch('addRack',this.rack);
		     },
		     handleDelete(data){
                this.spec = data.row;
		        this.dialogVisible_deleteSpec = true;
		     },
		     onClose(){
		     	this.dialogVisible_addRack= false;
		     	this.dialogVisible_rackUp=false;
		     	this.dialogVisible_rackDown=false;
		     },
		     onSearch(){
		      	this.$store.dispatch('queryRack',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue,
		      	});
		     }
    	},

        data: function(){
            return {
                    dialogVisible_addRack:false,
			        dialogVisible_rackUp:false,
			        dialogVisible_rackDown:false,
			        currentField:{
			          value: 'rackId',
			          label: 'rackId'
			        },
					currentPage:1,
			        pageSize:10,
			        currentValue:'',
			        rack:{
			          rackId:'',
                      status:'',
			          createTime:'',
			          modifyTime:'',
			        }
            }
        },
        computed:{
        	...mapGetters({
        		rackList: 'getRackList',
        		fieldList: 'getRackFieldList',
        		total: 'getRackTotal',
                isLogin: 'getLoginState',
        		promptMessage: 'getPromptMessage'
        	})
        },
        created (){
        	this.$store.dispatch('queryRack');
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

