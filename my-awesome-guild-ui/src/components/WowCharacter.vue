<template>
    <div style="border: 1px solid black; margin: 5px;">
        hey
        {{character.name}}
    </div>
</template>

<script>

    import axios from "axios";
    import EventBus from "../event-bus";

    export default {
        name: 'WowCharacter',
        props: {
            characterId: null
        },
        data: function () {
            return {
                character: null
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
                        }
                    })
            },
        },
        mounted() {
            this.loadCharacter();
        }
    }
</script>
