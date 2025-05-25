import type { RouteRecordRaw } from 'vue-router';

import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'lucide:layout-dashboard',
      order: 0,
      title: $t('page.log.title'),
    },
    name: 'Review',
    path: '/review',
    children: [
      {
        name: 'CommentReview',
        path: '/commentReview',
        component: () => import('#/views/review/comment/index.vue'),
        meta: {
          affixTab: true,
          icon: 'lucide:area-chart',
          title: $t('page.review.comment'),
        },
      },
    ],
  },
];

export default routes;
