import './assets/css/main.css';
import Vue from 'vue'
import VueRouter from 'vue-router'
import auth from './auth'
import Main from './layout/Main.vue'
import Login from './components/Login.vue'
import Guild from './components/Guild.vue'
import NewUser from "./components/NewUser";
import ImportWowCharacter from "./components/ImportWowCharacter";
import CharactersManager from "./components/CharactersManager";
import WowCharacterPage from "./components/WowCharacterPage";

Vue.config.productionTip = false;
Vue.use(VueRouter);

function requireAuth(to, from, next) {
    if (!auth.isLoggedIn()) {
        next({
            path: '/login',
            query: {redirect: to.fullPath}
        })
    } else {
        next()
    }
}

const routes = [
    // {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/sign-up', component: NewUser},
    {path: '/import-character', component: ImportWowCharacter, beforeEnter: requireAuth},
    {path: '/character-manager', component: CharactersManager, beforeEnter: requireAuth},
    {path: '/character/:characterId', component: WowCharacterPage, props: true},
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
