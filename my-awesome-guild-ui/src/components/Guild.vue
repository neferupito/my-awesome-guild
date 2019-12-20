<template>
    <div v-show="guild != null">
        {{guild.realm.name}} - {{guild.name}} - {{guild.faction}} - {{guild.lastUpdate}}
        <div v-for="id in rosterIds" v-bind:key="id.wowCharacterId">
            <WowCharacter :character-id="id.wowCharacterId" />
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import EventBus from "../event-bus";
    import WowCharacter from "./WowCharacter.vue";

    export default {
        name: 'Guild',
        components: {
            WowCharacter
        },
        props: {
            guildId: null
        },
        data: function () {
            return {
                guild: null,
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
            }
        },
        mounted() {
            this.loadGuild();
        }
    }
</script>
