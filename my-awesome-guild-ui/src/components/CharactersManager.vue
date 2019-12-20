<template>
    <div>
        <div v-if="characters !== null && characters.length" style="border: 1px solid black">
            <p v-for="char in characters" v-bind:key="char.id"><img :src="char.avatarUrl" width="50px" height="50px" />
                {{char.name}} {{char.lastUpdate}}
                <button @click="refreshCharacter(char.id)">REFRESH</button>
                <button @click="deleteCharacter(char.id)">DELETE</button>
            </p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from "../event-bus";

    export default {
        name: 'CharactersManager',
        props: {
            baseUser: null
        },
        data: function () {
            return {
                user: null,
                characters: null
            };
        },
        methods: {
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
            deleteCharacter(id) {
                axios
                    .delete('http://localhost:8080/wow-character/' + id)
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
            this.user = this.baseUser;
            this.getCharacters();
        },
        created() {
            EventBus.$on('NEW_USER', (user) => {
                this.user = user;
                this.getCharacters();
            });
        }
    }
</script>
