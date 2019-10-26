<template>
    <div id="app">
        <NewUser />
        <ImportWowCharacter />

    </div>
</template>

<script>
  import NewUser from './components/NewUser.vue'
  import ImportWowCharacter from './components/ImportWowCharacter.vue'

  export default {
        name: 'app',
        components: {
            NewUser,
            ImportWowCharacter
        }
  }
</script>

