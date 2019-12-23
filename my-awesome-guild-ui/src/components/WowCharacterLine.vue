<template>
    <div class="rounded bg-white mb-1">
        <img :src="avatar" width="50px" height="50px" /> -
        <router-link v-if="character !== null" :to="'/character/'+character.id">{{character.name}}</router-link> -
        <span v-if="guild !== null"><router-link :to="'/guild/'+guild.id">{{guild.name}}</router-link> {{rank}}</span>
    </div>
</template>

<script>

    import axios from "axios";
    import EventBus from "../event-bus";

    export default {
        name: 'WowCharacterLine',
        props: {
            characterId: null
        },
        data: function () {
            return {
                character: null,
                avatar: null,
                guild: null,
                rank: null
            };
        },
        methods: {
            loadCharacter() {
                axios
                    .get('http://localhost:8080/wow-character/' + this.characterId)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.character = response.data;
                            this.avatar = this.character.avatarUrl;
                            this.loadGuild();
                        }
                    })
            },
            loadGuild() {
                axios
                    .get('http://localhost:8080/wow-character/' + this.characterId + '/guild')
                    // .catch(reason => {
                    // EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    // })
                    .then(response => {
                        if (response != null) {
                            this.guild = response.data.guild;
                            this.rank = response.data.rank;
                        }
                    })
            }
        },
        mounted() {
            this.loadCharacter();
        }
    }
</script>
