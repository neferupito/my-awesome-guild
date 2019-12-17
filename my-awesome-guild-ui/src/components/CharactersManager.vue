<template>
    <div>
        <div v-show="characters.length" style="border: 1px solid black">
            <p v-for="char in characters" v-bind:key="char.id">xxx {{char.name}}
                <button @click="refreshCharacter(char.id)">REFRESH</button>
                <button @click="deleteCharacter(char.id)">DELETE</button>
                <!--                <span-->
                <!--                    v-show="char.user != null">{{char.user.email}}</span>-->
            </p>
        </div>

    </div>
</template>

<script>
    import axios from 'axios';
    import EventBus from "../event-bus";
    // import EventBus from '../event-bus';

    export default {
        name: 'CharactersManager',
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
                    .get('http://localhost:8080/characters?userEmail=' + this.user.email)
                    .then(response => {
                        this.characters = response.data;
                    })
            },
            refreshCharacter(id) {
                axios
                    .get('http://localhost:8080/refresh-character?wowCharacterId=' + id)
                    .then(response => {
                        if (response.status != 200) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', "REFRESH RATE");
                        }
                        this.getCharacters();
                    });
            },
            deleteCharacter(id) {
                axios
                    .get('http://localhost:8080/delete-character?wowCharacterId=' + id)
                    .then(response => {
                        if (response.status != 200) {
                            EventBus.$emit('SHOW_ERROR_MESSAGE', "DELETE RATE");
                        }
                        this.getCharacters();
                    });
            }
        },
        mounted() {
            this.getCharacters();
        }

    }
</script>
