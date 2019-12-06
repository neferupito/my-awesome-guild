<template>
    <div id="app">
        <button @click="generateData">Generate WOW data</button>
        <NewUser />
        <ImportWowCharacter />

        <div>
            <p v-show="existingRules.length" v-for="rule in existingRules" v-bind:key="rule.id"><a @click="set(rule)">{{rule}}</a>

        </div>

    </div>
</template>

<script>
    import NewUser from './components/NewUser.vue'
    import ImportWowCharacter from './components/ImportWowCharacter.vue'
    import axios from 'axios';

    export default {
        name: 'app',
        components: {
            NewUser,
            ImportWowCharacter
        },
        data() {
            return {
                characters: null
            }
        },
        methods: {
            generateData() {
                axios
                    .get('http://localhost:8080/data/gen')
                // .then(response => {
                //     this.realms = response.data.content;
                // })
            },
            getCharacters() {
                axios
                    .get('http://localhost:8080/data/gen')
                // .then(response => {
                //     this.realms = response.data.content;
                // })
            }
        }
    }
</script>

