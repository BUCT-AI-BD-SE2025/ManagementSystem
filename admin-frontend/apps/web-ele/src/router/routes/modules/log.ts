import type { RouteRecordRaw } from 'vue-router';

import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'lucide:layout-dashboard',
      order: 0,
      title: $t('page.log.title'),
    },
    name: 'Log',
    path: '/log',
    children: [
      {
        name: 'LoginLog',
        path: '/loginLog',
        component: () => import('#/views/log/loginLog/index.vue'),
        meta: {
          affixTab: true,
          icon: 'lucide:area-chart',
          title: $t('page.log.loginLog'),
        },
      },
      {
        name: 'OperationLog',
        path: '/operationLog',
        component: () => import('#/views/log/operationLog/index.vue'),
        meta: {
          icon: 'lucide:layout-dashboard',
          title: $t('page.log.operationLog'),
        },
      },
    ],
  },
];

export default routes;
