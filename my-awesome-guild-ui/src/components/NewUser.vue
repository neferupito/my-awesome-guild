<template>
    <div>
        <input id="email" v-model="email"
               placeholder="email" />
        <button @click="createUser">CREATE</button>
        <br />
        <br />
        <div v-if="users.length" style="border: 1px solid black">
            <p v-for="user in users" v-bind:key="user.id">x {{user.email}}</p>
        </div>

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
                            EventBus.$emit('REFRESH_USERS');
                        }
                    });
            }
        },
        mounted() {
            this.getUsers();
        }
    }
</script>
