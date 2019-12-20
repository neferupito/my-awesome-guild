<template>
    <div class="container">
        <Error />
        <!--        {path: '/', component: Home},-->
        <!--        {path: '/sign-up', component: NewUser},-->
        <!--        {path: '/import-character', component: ImportWowCharacter},-->
        <!--        {path: '/character-manager', component: CharactersManager},-->
        <!--        {path: '/character/:characterId', component: WowCharacter, props: true},-->
        <!--        {path: '/guild/:guildId', component: Guild, props: true},-->
        <ul>
            <li>
                <router-link to="/">/</router-link>
            </li>
            <li>
                <router-link to="/sign-up">sign up</router-link>
            </li>
            <li>
                <router-link to="/import-character">import</router-link>
            </li>
            <li>
                <router-link to="/character-manager">character mngr</router-link>
            </li>
        </ul>

        <p><select name="region" v-model="selectedRegion"
                   @change="notifyChangeRegion">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="region" v-for="region in regions" v-bind:key="region">{{region}}</option>
        </select></p>

        <p><select name="user" v-model="selectedUser" @change="notifyChangeUser">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="user" v-for="user in users" v-bind:key="user.id">{{user.email}}</option>
        </select></p>
        <br />
        <hr />
        <br />
        <router-view :base-region="selectedRegion" :base-user="selectedUser"></router-view>

        <!--        <slot></slot>-->
    </div>
</template>

<script>
    import Error from '../components/Error.vue'
    import axios from "axios";
    import EventBus from "../event-bus";

    export default {
        components: {
            Error
        },
        data() {
            return {
                users: null,
                regions: null,
                selectedUser: null,
                selectedRegion: null
            }
        },
        methods: {
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
            getRegions() {
                axios
                    .get('http://localhost:8080/regions')
                    .then(response => {
                        if (response.status !== 200) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', response.data);
                        } else {
                            this.regions = response.data;
                        }
                    })
            },
            notifyChangeRegion() {
                EventBus.$emit('NEW_REGION', this.selectedRegion);
            },
            notifyChangeUser() {
                EventBus.$emit('NEW_USER', this.selectedUser);
            }
        },
        mounted() {
            this.getUsers();
            this.getRegions();
            EventBus.$on('REFRESH_USERS', () => {
                this.getUsers();
            });
        }
    }
</script>
