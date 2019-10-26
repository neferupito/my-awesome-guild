<template>
    <div>
        <select name="realm" v-model="selectedRealm">
            <option disabled value="">Choisissez</option>
            <option v-bind:value="realm" v-for="realm in realms" v-bind:key="realm.id">{{realm.name}}</option>
        </select>
        <input v-model="characterName" placeholder="Nom du personnage">
        {{selectedRealm}}
        {{characterName}}
        <button @click="importCharacter">FIND</button>
        <span v-if="importedCharacter !== null">{{importedCharacter}}</span>

    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'ImportWowCharacter',
        data: function () {
            return {
                realms: Array,
                selectedRealm: null,
                characterName: null,
                importedCharacter: null
            };
        },
        methods: {
            getRealms() {
                axios
                    .get('http://localhost:8080/realms')
                    .then(response => {
                        this.realms = response.data.content;
                    })
            },
            importCharacter() {
                axios
                    .get('http://localhost:8080/import/character?region=eu&slugRealm=' + this.selectedRealm.slug + '&name=' + this.characterName)
                    .then(response => {
                        // eslint-disable-next-line no-console
                        console.debug(response.data.content);
                        this.importedCharacter = response.data.content;
                    })
            }
        },
        mounted() {
            this.realms = this.getRealms();
        }

    }
</script>
