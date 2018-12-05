<template>

    <div class="widget">

        <Row class="vm-margin">
            <VmStateGroup :data="tasks">
            </VmStateGroup>
        </Row>
        <Row :gutter="16">
            <Col :lg="8">
            <VmCircle class="demo-circle" title="CPU" usedName="使用" :usedValue="resources.usedCpu" restName="剩余" :restValue="resources.restCpu">
            </VmCircle>
            </Col>
            <Col :lg="8">
            <VmCircle class="demo-circle" title="Memory" usedName="使用" :usedValue="resources.usedMem" restName="剩余" :restValue="resources.restMem" typeUnit="G">
            </VmCircle>
            </Col>

            <Col :lg="8">
            <VmCircle class="demo-circle" title="Disk" usedName="使用" :usedValue="resources.usedDisk" restName="剩余" :restValue="resources.restDisk" typeUnit="G">
            </VmCircle>
            </Col>
        </Row>

    </div>
</template>

<script>

    import VmCircle from './vm/vm-circle'
    import VmStateGroup from './vm/vm-state'
    import {mapGetters, mapActions} from 'vuex';

    export default{

        components: {
            VmStateGroup,
            VmCircle
        },

        created (){
            this.$store.dispatch('queryMesos');
        },

        computed: {
            ...mapGetters({
                resources: 'getMesosResource',
                tasks: 'getMesosTask'
            })
        },




    }
</script>