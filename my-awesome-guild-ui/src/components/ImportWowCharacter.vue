<template>
    <div>
        <select name="realm" v-model="selectedRealm">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="realm" v-for="realm in realms" v-bind:key="realm.id">{{realm.name}}</option>
        </select>
        <input v-model="characterName" placeholder="Nom du personnage">
        <button @click="importCharacter">FIND</button>
        <span v-if="foundCharacter !== null">{{foundCharacter.name}} {{foundCharacter.level}} {{foundCharacter.wowClass.name}} <span
                v-if="foundCharacter.user !== null">{{foundCharacter.user.email}}</span></span>
        <p v-if="foundCharacter !== null">
            <button @click="linkCharacter">LINK</button>
        </p>

        <br />
        <br />

        <div v-if="characters !== null && characters.length" style="border: 1px solid black">
            <p v-for="char in characters" v-bind:key="char.id"><img :src="char.avatarUrl" width="50px" height="50px" />
                {{char.name}}
                {{char.lastUpdate}}</p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from '../event-bus';

    export default {
        name: 'ImportWowCharacter',
        props: {
            baseUser: null,
            baseRegion: null
        },
        data: function () {
            return {
                user: null,
                region: null,
                realms: Array,
                selectedRealm: null,
                characterName: null,
                foundCharacter: null,
                characters: null
            };
        },
        methods: {
            getRealms() {
                axios
                    .get('http://localhost:8080/' + this.region + '/realms')
                    .catch(error => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', error.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.realms = response.data;
                        }
                    })
            },
            importCharacter() {
                axios
                    .get('http://localhost:8080/wow-character-import?region=' + this.region + '&slugRealm=' + this.selectedRealm.slug + '&name=' + this.characterName + '&userEmail=' + this.user.email)
                    .catch(error => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', error.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.foundCharacter = response.data;
                        }
                    });
            },
            linkCharacter() {
                axios
                    .get('http://localhost:8080/wow-character/' + this.foundCharacter.id + '/link?userEmail=' + this.user.email)
                    .catch(error => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', error.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.foundCharacter = null;
                            this.characterName = null;
                            this.getCharacters();
                        }
                    });
            },
            getCharacters() {
                axios
                    .get('http://localhost:8080/characters?userEmail=' + this.user.email)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.characters = response.data;
                        }
                    })
            }
        },
        mounted() {
            this.region = this.baseRegion;
            this.user = this.baseUser;
            this.getRealms();
            this.getCharacters();
            EventBus.$on('NEW_REGION', (region) => {
                this.region = region;
                this.getRealms();
            });
            EventBus.$on('NEW_USER', (user) => {
                this.user = user;
                this.getCharacters();
            });
        }

    }
</script>
