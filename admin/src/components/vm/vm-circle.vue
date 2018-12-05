<template>
    <Row type="flex" align="middle" class="vm-circle vm-panel">
        <Col span="12">
        <i-circle :percent="percent">
            <span style="font-size:24px">{{ percent }}%</span>
        </i-circle>
        </Col>
        <Col span="12">
        <div class="desc">
            <h2 class="title">{{ title }}</h2>
            <p>{{ usedName }}：{{ usedValue }} {{typeUnit}}</p>
            <p>{{ restName }}：{{ restValue }} {{typeUnit}}</p>
        </div>
        </Col>
    </Row>
</template>
<script>
    export default {
        name: 'VmCircle',
        props: {
            title: {
                type: String,
                default: 'Title'
            },
            typeUnit: {
                type: String,
                default: ''
            },
            usedName: {
                type: String,
                default: 'Used'
            },
            usedValue: {
                type: Number,
                default: 80
            },
            restName: {
                type: String,
                default: 'Rest'
            },
            restValue: {
                type: Number,
                default: 20
            }
        },
        computed: {
            percent: function () {
                return parseInt(this.usedValue / (this.usedValue + this.restValue) * 100)
            }
        }
    }
</script>
