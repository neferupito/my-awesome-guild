import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from './layout/Main.vue'
// import Home from './components/Home.vue'
import Guild from './components/Guild.vue'
import './assets/css/main.css';
import NewUser from "./components/NewUser";
import ImportWowCharacter from "./components/ImportWowCharacter";
import CharactersManager from "./components/CharactersManager";
import WowCharacter from "./components/WowCharacter";

Vue.config.productionTip = false;
Vue.use(VueRouter);

const routes = [
    // {path: '/', component: Home},
    {path: '/sign-up', component: NewUser},
    {path: '/import-character', component: ImportWowCharacter},
    {path: '/character-manager', component: CharactersManager},
    {path: '/character/:characterId', component: WowCharacter, props: true},
    {path: '/guild/:guildId', component: Guild, props: true}
    // { path: '/static', component: Hello, props: { name: findUser() }}
];
const router = new VueRouter({
    mode: 'history',
    routes
});

new Vue({
    router,
    render: h => h(Main),
})
    .$mount('#app');
