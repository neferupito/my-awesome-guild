export default {
    login(email) {
        localStorage.setItem('user', email);
        return true;
    },

    logout() {
        localStorage.removeItem('user');
    },

    isLoggedIn() {
        return localStorage.getItem('user') !== null;
    },

    loggedInUser() {
        return localStorage.getItem('user');
    }
}