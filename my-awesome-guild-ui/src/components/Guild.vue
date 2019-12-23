<template>
    <div v-if="guild != null">
        {{guild.realm.name}} - {{guild.name}} - {{guild.faction}} - {{guild.lastUpdate}} - ISGM? {{isUserGM}}
        <div v-for="id in rosterIds" v-bind:key="id.wowCharacterId">
            <WowCharacterLine :character-id="id.wowCharacterId" />
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import EventBus from "../event-bus";
    import WowCharacterLine from "./WowCharacterLine.vue";

    export default {
        name: 'Guild',
        components: {
            WowCharacterLine
        },
        props: {
            user: null,
            guildId: null
        },
        data: function () {
            return {
                guild: null,
                isUserGM: false,
                rosterIds: Array,
            };
        },
        methods: {
            loadGuild() {
                axios
                    .get('http://localhost:8080/wow-guild/' + this.guildId)
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.guild = response.data;
                            this.loadRosterIds();
                            if (this.user !== null) {
                                this.loadGuildMaster();
                            }
                        }
                    })
            },
            loadRosterIds() {
                axios
                    .get('http://localhost:8080/wow-guild/' + this.guildId + '/roster')
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            this.rosterIds = response.data;
                        }
                    })
            },
            loadGuildMaster() {
                axios
                    .get('http://localhost:8080/wow-guild/' + this.guildId + '/guild-master')
                    .catch(reason => {
                        EventBus.$emit('SHOW_ERROR_MESSAGE', reason.response.data);
                    })
                    .then(response => {
                        if (response != null) {
                            var gmChar = response.data;
                            if (gmChar.user !== null) {
                                if (this.user === gmChar.user.email) {
                                    this.isUserGM = true;
                                }
                            }
                        }
                    })
            }
        },
        mounted() {
            this.loadGuild();
        }
    }
</script>
