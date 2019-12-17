import Vue from 'vue';

const EventBus = new Vue();
export default EventBus;


// EventBus.$emit('TRIGGER_ADDED', this.newTrigger);
// mounted() {
//     EventBus.$on('CONFIG_ADDED', () => {
//         this.loadExistingGraphics();
//     });
// }


// data: ""
// status: 200
// statusText: ""
// headers: {content-length: "0"}
// config: {url: "http://localhost:8080/data/gen", method: "get", headers: {…}, transformRequest: Array(1), transformResponse: Array(1), …}
// request: XMLHttpRequest {readyState: 4, timeout: 0, withCredentials: false, upload: XMLHttpRequestUpload, onreadystatechange: ƒ, …}
// __proto__: Object