<template>
    <div>
        <select name="realm" v-model="selectedRealm">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="realm" v-for="realm in realms" v-bind:key="realm.id">{{realm.name}}</option>
        </select>
        <input v-model="characterName" placeholder="Nom du personnage">
        <button @click="importCharacter">FIND</button>
        <span v-if="foundCharacter !== null">{{foundCharacter.name}} {{foundCharacter.level}} {{foundCharacter.wowClass.name}}</span>
        <p v-if="foundCharacter !== null">
            <button @click="linkCharacter">LINK</button>
        </p>

        <p v-show="isError">{{errorStatus}} {{errorMessage}}</p>

        <br />
        <br />

        <div v-show="characters.length" style="border: 1px solid black">
            <p  v-for="char in characters" v-bind:key="char.id">xxx {{char.name}}
                <!--                <span-->
                <!--                    v-show="char.user != null">{{char.user.email}}</span>-->
            </p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from '../event-bus';

    export default {
        name: 'ImportWowCharacter',
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
                isError: false,
                errorStatus: null,
                errorMessage: null,
                foundCharacter: null,
                characters: null
            };
        },
        methods: {
            getRealms() {
                axios
                    .get('http://localhost:8080/' + this.region + '/realms')
                    .then(response => {
                        this.realms = response.data.content;
                    })
            },
            importCharacter() {
                axios
                    .get('http://localhost:8080/find-character?region=' + this.region + '&slugRealm=' + this.selectedRealm.slug + '&name=' + this.characterName + '&userEmail=' + this.user.email)
                    .then(response => {
                        // eslint-disable-next-line no-console
                        console.debug(response.data);
                        if (response.data.error) {
                            this.isError = true;
                            EventBus.$emit('SHOW_ERROR_MESSAGE', response.data.externalHttpMessage);
                            // this.errorStatus = response.data.externalHttpStatus;
                            // this.errorMessage = response.data.externalHttpMessage;
                        } else {
                            this.isError = false;
                            this.errorStatus = null;
                            this.errorMessage = null;
                            this.foundCharacter = response.data.content;
                        }
                    });
            },
            linkCharacter() {
                axios
                    .get('http://localhost:8080/link-character?wowCharacterId=' + this.foundCharacter.id + '&userEmail=' + this.user.email)
                    .then(response => {
                        // eslint-disable-next-line no-console
                        console.debug(response.data);
                        if (response.data.error) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', response.data.externalHttpMessage);
                            this.isError = true;
                            // this.errorStatus = response.data.externalHttpStatus;
                            // this.errorMessage = response.data.externalHttpMessage;
                        } else {
                            this.isError = false;
                            this.errorStatus = null;
                            this.errorMessage = null;
                            this.foundCharacter = null;
                            this.getCharacters();
                        }
                    });
            },
            getCharacters() {
                axios
                    .get('http://localhost:8080/characters?userEmail=' + this.user.email)
                    .then(response => {
                        this.characters = response.data;
                    })
            }
        },
        mounted() {
            this.region = this.baseRegion;
            this.getRealms();
            this.getCharacters();
            EventBus.$on('NEW_REGION', (region) => {
                this.region = region;
                this.getRealms();
            });
        }

    }
</script>
