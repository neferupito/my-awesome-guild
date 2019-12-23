<template>
    <div class="rounded bg-white mb-1">
        <p><img :src="character.avatarUrl" width="50px" height="50px" /> {{character.name}}</p>
        <p v-if="guild != null">
            <router-link :to="'/guild/'+guild.id">{{guild.name}}</router-link>
        </p>
        <p>{{character.level}} {{character.wowClass.name}} {{character.mainSpec.name}}</p>
        <p>Claimed? {{character.claimed}} <span v-if="character.claimed">by {{character.user.email}}</span></p>
        <p class="text-muted">{{character.lastUpdate}}
            <button @click="refreshCharacter()">REFRESH</button>
        </p>
    </div>
</template>

<script>

    import axios from "axios";
    import EventBus from "../event-bus";

    export default {
        name: 'WowCharacterPage',
        props: {
            characterId: null
        },
        data: function () {
            return {
                character: null,
                guild: null
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
                        }
                    })
            },
            refreshCharacter() {
                axios
                    .put('http://localhost:8080/wow-character/' + this.character.id)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.loadCharacter();
                        }
                    });
            },
        },
        mounted() {
            this.loadCharacter();
        }
    }
</script>
