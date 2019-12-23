<template>
    <div>
        <form class="form-signin text-center">
            <label for="email-wow" class="sr-only">Email</label>
            <input id="email-wow" class="form-control" v-model="email"
                   type="text" placeholder="email" required autofocus />
            <button type="submit" @click="createUser" class="btn btn-lg btn-primary btn-block">S'inscrire</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from "../event-bus";

    export default {
        name: 'NewUser',
        data: function () {
            return {
                users: null,
                email: null,
                createdUser: null
            };
        },
        methods: {
            getUsers() {
                axios
                    .get('http://localhost:8080/users')
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.users = response.data;
                        }
                    })
            },
            createUser() {
                const headers = {
                    'Content-Type': 'application/json'
                };

                let json = "{" +
                    "\"email\": \"" + this.email + "\"" +
                    "}";
                axios
                    .post('http://localhost:8080/user', json, {headers: headers})
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.createdUser = response.data;
                            this.getUsers();
                        }
                    });
            }
        },
        mounted() {
            this.getUsers();
        }
    }
</script>
