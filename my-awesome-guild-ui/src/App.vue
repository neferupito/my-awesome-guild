<template>
    <div id="app">
        <Error />

        <table style="width: 100%">
            <tr>
                <td style="width: 50%">
                    <button @click="setScope('new_user')">Sign up</button>
                    <button v-if="selectedUser !== null && selectedRegion !== null" @click="setScope('import_char')">Import WoW character</button>
                    <button v-else disabled>Import WoW character</button>
                    <button v-if="selectedUser !== null" @click="setScope('mng_chars')">Manage WoW characters</button>
                    <button v-else disabled>Manage WoW characters</button>
                </td>
                <td style="width: 50%; text-align: right"><select name="region" v-model="selectedRegion"
                                                                  @change="notifyChangeRegion">
                    <option disabled value="">Choisissez</option>
                    <option v-bind:value="region" v-for="region in regions" v-bind:key="region">{{region}}</option>
                </select>

                    <select name="user" v-model="selectedUser" @change="notifyChangeUser">
                        <option disabled value="">Choisissez</option>
                        <option v-bind:value="user" v-for="user in users" v-bind:key="user.id">{{user.email}}</option>
                    </select></td>
            </tr>
        </table>
        <br />
        <hr />
        <br />
        <NewUser v-if="scope === 'new_user'" />
        <ImportWowCharacter v-if="scope === 'import_char'" :base-user=selectedUser :base-region=selectedRegion />
        <CharactersManager v-if="scope === 'mng_chars'" :base-user=selectedUser />

        <!--        <button @click="generateData">Generate WOW data</button>-->

    </div>
</template>

<script>
    import Error from './components/Error.vue'
    import NewUser from './components/NewUser.vue'
    import ImportWowCharacter from './components/ImportWowCharacter.vue'
    import CharactersManager from './components/CharactersManager.vue'
    import axios from 'axios';
    import EventBus from './event-bus';

    export default {
        name: 'app',
        components: {
            Error,
            NewUser,
            ImportWowCharacter,
            CharactersManager
        },
        data() {
            return {
                scope: null,
                users: null,
                regions: null,
                selectedUser: null,
                selectedRegion: null
            }
        },
        methods: {
            generateData() {
                axios
                    .get('http://localhost:8080/data/gen')
                    .then(response => {
                        if (response.status === 200) {
                            this.getUsers();
                            this.getRegions();
                        }
                    });
            },
            setScope(scope) {
                this.scope = scope;
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
        }
    }
</script>

