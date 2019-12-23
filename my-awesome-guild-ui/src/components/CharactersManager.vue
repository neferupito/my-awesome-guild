<template>
    <div>
        <div v-if="characters !== null">
            <p v-for="char in characters" v-bind:key="char.id">
                <WowCharacterLine :character-id="char.id" />
                <button @click="refreshCharacter(char.id)">REFRESH</button>
                <button @click="deleteCharacterLink(char.id)">DELETE</button>
            </p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from "../event-bus";
    import WowCharacterLine from "./WowCharacterLine";

    export default {
        name: 'CharactersManager',
        components: {WowCharacterLine},
        props: {
            user: null
        },
        data: function () {
            return {
                characters: null
            };
        },
        methods: {
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
            },
            refreshCharacter(id) {
                axios
                    .put('http://localhost:8080/wow-character/' + id)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.getCharacters();
                        }
                    });
            },
            deleteCharacterLink(id) {
                axios
                    .delete('http://localhost:8080/wow-character/' + id + '/link?userEmail=' + this.user)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.getCharacters();
                        }
                    });
            }
        },
        mounted() {
            this.getCharacters();
        }
    }
</script>
