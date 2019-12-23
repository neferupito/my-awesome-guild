<template>
    <div>
        <form class="form-signin text-center">
            <label for="email-wow" class="sr-only">Email</label>
            <p><select name="user" v-model="email" id="email-wow">
                <option v-bind:value="user.email" v-for="user in users" v-bind:key="user.id">{{user.email}}</option>
            </select></p>
            <!--            <input id="email-wow" class="form-control" v-model="email"-->
            <!--                   type="text" placeholder="email" required autofocus />-->
            <button type="submit" @click="login" class="btn btn-lg btn-primary btn-block">Login</button>
        </form>
    </div>
</template>

<script>
    import auth from '../auth'
    import axios from "axios";
    import EventBus from "../event-bus";

    export default {
        name: 'Login',
        data: function () {
            return {
                users: null,
                email: null
            };
        },
        methods: {
            login() {
                auth.login(this.email);
                // eslint-disable-next-line no-console
                // console.warn('#### '+this.$route.query.redirect);
                this.$router.push(this.$route.query.redirect)
            },
            getUsers() {
                axios
                    .get('http://localhost:8080/users')
                    .then(response => {
                        if (response.status !== 200) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', response.data);
                        } else {
                            this.users = response.data;
                        }
                    })
            },
        },
        mounted() {
            this.getUsers();
        }
    }
</script>
