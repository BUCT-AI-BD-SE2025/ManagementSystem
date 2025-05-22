import type { RouteRecordRaw } from 'vue-router';

import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'lucide:layout-dashboard',
      order: 0,
      title: $t('page.dashboard.title'),
    },
    name: 'Management',
    path: '/management',
    children: [
      {
        name: 'User',
        path: '/user',
        component: () => import('#/views/management/user/index.vue'),
        meta: {
          affixTab: true,
          icon: 'lucide:area-chart',
          title: $t('page.management.user'),
        },
      },
      {
        name: 'Role',
        path: '/role',
        component: () => import('#/views/management/role/index.vue'),
        meta: {
          icon: 'lucide:layout-dashboard',
          title: $t('page.management.role'),
        },
      },
      {
        name: 'Permission',
        path: '/permission',
        component: () => import('#/views/management/permission/index.vue'),
        meta: {
          icon: 'lucide:layout-dashboard',
          title: $t('page.management.permission'),
        }
      },
      {
        name: 'Artifact',
        path: '/artifact',
        component: () => import('#/views/management/artifact/index.vue'),
        meta: {
          icon: 'carbon:workspace',
          title: $t('page.management.artifact'),
        },
      },
      {
        name: 'Comment',
        path: '/comment',
        component: () => import('#/views/management/comment/index.vue'),
        meta: {
          icon: 'lucide:layout-dashboard',
          title: $t('page.management.comment'),
        }
      },
      {
        name: 'Database',
        path: '/database',
        component: () => import('#/views/management/database/index.vue'),
        meta: {
          icon: 'lucide:layout-dashboard',
          title: $t('page.management.database'),
        }
      }
    ],
  },
];

export default routes;
