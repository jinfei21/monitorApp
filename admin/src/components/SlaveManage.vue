<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">集群管理</el-breadcrumb-item>
                    <el-breadcrumb-item>Slave列表</el-breadcrumb-item>
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
            <el-table :data="slaveList" style="width: 100%" empty-text="暂无数据"  border fit>
                <template scope="scope">

                    <el-table-column label="ID" prop="id" align="center" sortable width="120"></el-table-column>
                    <el-table-column label="SlaveID" prop="slaveId" align="center"></el-table-column>
                    <el-table-column label="RackID" prop="rackId" align="center"></el-table-column>
                    <el-table-column label="Host" prop="host" align="center"></el-table-column>
                    <el-table-column label="状态" prop="status" align="center" width="240"></el-table-column>
                    <el-table-column label="创建时间" prop="createTime" align="center"width="220"></el-table-column>
                    <el-table-column label="修改时间" prop="modifyTime" align="center"width="220"></el-table-column>

                    <el-table-column v-if="isLogin" label="操作" align="center" width="200">
                        <template scope="scope">
                            <label v-if="scope.row.status == 'SLAVE_DECOMISSION'">
                                <el-button @click="handleUp(scope)" size="small" type="success">上线</el-button>
                            </label>
                            <label v-if="scope.row.status == 'UP'">
                                <el-button @click="handleDown(scope)" size="small" type="danger">下线</el-button>
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

        <el-dialog title="Slave上线" :visible.sync="dialogVisible_slaveUp" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="slave" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.id"></el-input>
                </el-form-item>
                <el-form-item label="SlaveID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.slaveId"></el-input>
                </el-form-item>
                <el-form-item label="RackID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.rackId"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="upSlave">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否下线该Slave！" :visible.sync="dialogVisible_slaveDown" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="slave" label-width="100px">
                <el-form-item label="ID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.id"></el-input>
                </el-form-item>
                <el-form-item label="SlaveID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.slaveId"></el-input>
                </el-form-item>
                <el-form-item label="RackID">
                    <el-input :disabled="true" style="width:350px" v-model="slave.rackId"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="downSlave">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="创建Slave" :visible.sync="dialogVisible_addSlave" size="tiny" :before-close="handleClose">
            <el-form ref="form" :model="slave" label-width="100px">
                <el-form-item label="SlaveID*">
                    <el-input style="width:300px" v-model="slave.slaveId"></el-input>
                </el-form-item>
                <el-form-item label="RackID*">
                    <el-input style="width:300px" v-model="slave.rackId"></el-input>
                </el-form-item>
                <el-form-item label="Host*">
                    <el-input style="width:300px" v-model="slave.host"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addSlave">提交</el-button>
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
		      	this.$store.dispatch('querySlave',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue
		      	});
		     },
		     handleCurrentChange(){
		      	this.$store.dispatch('querySlave',{
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
                this.slave = data.row;
		        this.dialogVisible_slaveUp = true;
		     },
		     handleDown(data){
                this.slave = data.row;
		        this.dialogVisible_slaveDown = true;
		     },
		     handleAdd(){
		      	this.slave={
			          slaveId:null,
			          rackId:null,
			          host:null,
			        };
		      	this.dialogVisible_addSlave = true;
		     },
		     upSlave(){
		        this.dialogVisible_slaveUp= false;
		      	this.$store.dispatch('upSlave',{
                    id: this.slave.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },
		     downSlave(){
		        this.dialogVisible_slaveDown= false;
		      	this.$store.dispatch('downSlave',{
                    id: this.slave.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },
		     addSlave(){
		     	this.dialogVisible_addSlave= false;
		      	this.$store.dispatch('addSlave',this.slave);
		     },
		     handleDelete(data){
                this.spec = data.row;
		        this.dialogVisible_deleteSpec = true;
		     },
		     deleteSlave(){
		        this.dialogVisible_deleteSlave = false;
		      	this.$store.dispatch('deleteSlave',{
                    id: this.slave.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        field: this.currentField,
                        keyword: this.currentValue
                    }
                });
		     },

		     onClose(){
		     	this.dialogVisible_slaveUp= false;
		     	this.dialogVisible_slaveDown= false;
                this.dialogVisible_addSlave=false;
		     },
		     onSearch(){
		      	this.$store.dispatch('querySlave',{
		      		pageSize:this.pageSize,
		      		page:this.currentPage,
		      		field:this.currentField,
		      		keyword:this.currentValue
		      	});
		    }
    	},

        data: function(){
            return {
			        dialogVisible:false,
			        dialogVisible_slaveUp:false,
			        dialogVisible_slaveDown:false,
			        dialogVisible_addSlave:false,
			        currentField:{
			          value: 'slaveId',
			          label: 'SlaveId'
			        },
			        currentPage:1,
			        pageSize:10,
			        currentValue:'',
			        slave:{
			          slaveId:'',
			          rackId:'',
			          host:'',
			          createTime:'',
			        }
            }
        },
        computed:{
        	...mapGetters({
        		slaveList: 'getSlaveList',
        		fieldList: 'getSlaveFieldList',
        		total: 'getSlaveTotal',
                isLogin: 'getLoginState',
                promptMessage: 'getPromptMessage'
        	})
        },
        created (){
        	this.$store.dispatch('querySlave');
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

