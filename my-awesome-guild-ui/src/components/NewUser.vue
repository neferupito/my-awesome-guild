<template>
    <div>
        <input id="email" v-model="email"
               placeholder="email" />
        <button @click="createUser">CREATE</button>
        <br />
        <br />
        <div style="border: 1px solid black">
            <p v-show="users.length" v-for="user in users" v-bind:key="user.id">xxx {{user.email}}</p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';

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
                    .then(response => {
                        this.users = response.data.content;
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
                    .then(response => {
                        this.createdUser = response.data;
                        // if (response.status === 200) {
                        //     this.loadExistingGraphics();
                        // }
                        this.getUsers();
                    });
            }
        },
        mounted() {
            this.getUsers();
        }
    }
</script>
