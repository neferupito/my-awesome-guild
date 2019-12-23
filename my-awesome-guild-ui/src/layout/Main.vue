<template>
    <div id="app">
        <header>
            <Error />
            <nav class="navbar navbar-expand-md navbar-dark bg-dark mt-2 container rounded">
                <a class="navbar-brand" href="/">Awesome</a>
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item"> <!-- + .active-->
                        <router-link to="/" class="nav-link">/</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/login" class="nav-link">login</router-link>
                    </li>
                    <li class="nav-item">
                        <a @click="logout()" class="nav-link">logout</a>
                    </li>
                    <li class="nav-item">
                        <router-link to="/sign-up" class="nav-link">sign up</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/import-character" class="nav-link">import</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/character-manager" class="nav-link">character mngr</router-link>
                    </li>
                </ul>
                <a>auth = {{getLoggedInUser()}}</a>
                <p><select name="region" v-model="selectedRegion"
                           @change="notifyChangeRegion">
                    <!--                <option disabled value="">Choisissez</option>-->
                    <option v-bind:value="region" v-for="region in regions" v-bind:key="region">{{region}}</option>
                </select></p>


            </nav>
        </header>
        <main role="main">
            <router-view :user="getLoggedInUser()" :base-region="selectedRegion"
                         class="container bg-light mt-2 p-2"></router-view>
        </main>
        <!--        <slot></slot>-->
        <!--        <footer>-->
        <!--            je suis le footer-->
        <!--        </footer>-->
    </div>
</template>

<script>
    import Error from '../components/Error.vue'
    import axios from "axios";
    import EventBus from "../event-bus";
    import auth from '../auth'

    export default {
        components: {
            Error
        },
        data() {
            return {
                users: null,
                regions: null,
                selectedRegion: null,
                isLoggedIn: false
            }
        },
        methods: {
            getLoggedInUser() {
                return auth.loggedInUser();
            },
            logout() {
                auth.logout();
                this.$router.push({ path: '/#' })
            },
            getRegions() {
                axios
                    .get('http://localhost:8080/regions')
                    .then(response => {
                        if (response.status !== 200) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', response.data);
                        } else {
                            this.regions = response.data;
                            this.selectedRegion = this.regions[0];
                        }
                    })
            },
            notifyChangeRegion() {
                EventBus.$emit('NEW_REGION', this.selectedRegion);
            }
        },
        mounted() {
            this.getRegions();
        }
    }
</script>
