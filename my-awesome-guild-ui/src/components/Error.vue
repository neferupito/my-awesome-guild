<template>
    <div v-if="message != null" class="container bg-danger text-center rounded mt-2">{{message}}
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
                setTimeout(() => {
                    this.eraseMessage();
                }, 2000);
            });
        }
    }
</script>
