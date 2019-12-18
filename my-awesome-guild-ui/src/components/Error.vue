<template>
    <div>
        <div v-if="message != null"  class="error">{{message}}
            <button @click="eraseMessage">OK</button>
            <br />
            <br />
        </div>
    </div>
</template>

<script>

    import EventBus from "../event-bus";

    export default {
        name: 'Error',
        data: function () {
            return {
                message: null
            };
        },
        methods: {
            eraseMessage() {
                this.message = null;
            }
        },
        mounted() {
            EventBus.$on('SHOW_ERROR_MESSAGE', (message) => {
                this.message = message;
            });
        }
    }
</script>
