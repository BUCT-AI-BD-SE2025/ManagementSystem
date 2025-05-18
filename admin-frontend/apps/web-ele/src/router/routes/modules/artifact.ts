import type { RouteRecordRaw } from 'vue-router'

import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    path: '/artifact',
    name: 'Artifact',
    component: () => import('#/views/artifact/index.vue'),
    meta: {
      title: $t('page.artifact.title'),
    },
  },
]

export default routes
