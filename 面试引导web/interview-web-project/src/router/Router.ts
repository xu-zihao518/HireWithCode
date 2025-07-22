import {createRouter, createWebHashHistory} from 'vue-router'
import InterviewGuide from '@/components/InterviewGuide.vue'
import Welcome from '@/components/Welcome.vue'

const router=createRouter({
    history:createWebHashHistory(),
    routes:[
         {
            path: '/',
            name: 'Welcome',
            component: Welcome
        },
        {
            path: '/interviewguide',
            name: 'InterviewGuide',
            component: InterviewGuide
        }
    ]
})
export default router