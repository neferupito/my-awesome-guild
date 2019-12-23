<template>
    <div>
        <select name="realm" v-model="selectedRealm">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="realm" v-for="realm in realms" v-bind:key="realm.id">{{realm.name}}</option>
        </select>
        <input v-model="characterName" placeholder="Nom du personnage">
        <button @click="importCharacter">FIND</button>
        <span v-if="foundCharacter !== null"> <WowCharacterLine :character-id="foundCharacter.id" /> <span
                v-if="foundCharacter.claimed">{{foundCharacter.user.email}}</span></span>
        <p v-if="foundCharacter !== null">
            <button @click="linkCharacter">LINK</button>
        </p>

        <br />
        <br />

        <div v-if="characters !== null && characters.length">
            <p v-for="char in characters" v-bind:key="char.id">
                <WowCharacterLine :character-id="char.id" />
            </p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from '../event-bus';
    import WowCharacterLine from "./WowCharacterLine";

    export default {
        name: 'ImportWowCharacter',
        components: {WowCharacterLine},
        props: {
            user: null,
            baseRegion: null
        },
        data: function () {
            return {
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
                    .get('http://localhost:8080/wow-character-import?region=' + this.region + '&slugRealm=' + this.selectedRealm.slug + '&name=' + this.characterName + '&userEmail=' + this.user)
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
                    .get('http://localhost:8080/wow-character/' + this.foundCharacter.id + '/link?userEmail=' + this.user)
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
                    .get('http://localhost:8080/characters?userEmail=' + this.user)
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
            this.getRealms();
            this.getCharacters();
        },
        created() {
            EventBus.$on('NEW_REGION', (region) => {
                this.region = region;
                this.getRealms();
            });
        }
    }
</script>
