/* prettier-ignore-start */

/* eslint-disable */

// @ts-nocheck

// noinspection JSUnusedGlobalSymbols

// This file is auto-generated by TanStack Router

// Import Routes

import { Route as rootRoute } from './pages/__root'
import { Route as LoginImport } from './pages/login'
import { Route as LayoutImport } from './pages/_layout'
import { Route as AuthImport } from './pages/_auth'
import { Route as IndexImport } from './pages/index'
import { Route as LayoutRankingImport } from './pages/_layout/ranking'
import { Route as LayoutGameImport } from './pages/_layout/game'
import { Route as AuthAddImport } from './pages/_auth/add'
import { Route as AuthProfileIndexImport } from './pages/_auth/profile/index'
import { Route as AuthProfileResetPasswordImport } from './pages/_auth/profile/reset-password'

// Create/Update Routes

const LoginRoute = LoginImport.update({
  path: '/login',
  getParentRoute: () => rootRoute,
} as any)

const LayoutRoute = LayoutImport.update({
  id: '/_layout',
  getParentRoute: () => rootRoute,
} as any)

const AuthRoute = AuthImport.update({
  id: '/_auth',
  getParentRoute: () => rootRoute,
} as any)

const IndexRoute = IndexImport.update({
  path: '/',
  getParentRoute: () => rootRoute,
} as any)

const LayoutRankingRoute = LayoutRankingImport.update({
  path: '/ranking',
  getParentRoute: () => LayoutRoute,
} as any)

const LayoutGameRoute = LayoutGameImport.update({
  path: '/game',
  getParentRoute: () => LayoutRoute,
} as any)

const AuthAddRoute = AuthAddImport.update({
  path: '/add',
  getParentRoute: () => AuthRoute,
} as any)

const AuthProfileIndexRoute = AuthProfileIndexImport.update({
  path: '/profile/',
  getParentRoute: () => AuthRoute,
} as any)

const AuthProfileResetPasswordRoute = AuthProfileResetPasswordImport.update({
  path: '/profile/reset-password',
  getParentRoute: () => AuthRoute,
} as any)

// Populate the FileRoutesByPath interface

declare module '@tanstack/react-router' {
  interface FileRoutesByPath {
    '/': {
      id: '/'
      path: '/'
      fullPath: '/'
      preLoaderRoute: typeof IndexImport
      parentRoute: typeof rootRoute
    }
    '/_auth': {
      id: '/_auth'
      path: ''
      fullPath: ''
      preLoaderRoute: typeof AuthImport
      parentRoute: typeof rootRoute
    }
    '/_layout': {
      id: '/_layout'
      path: ''
      fullPath: ''
      preLoaderRoute: typeof LayoutImport
      parentRoute: typeof rootRoute
    }
    '/login': {
      id: '/login'
      path: '/login'
      fullPath: '/login'
      preLoaderRoute: typeof LoginImport
      parentRoute: typeof rootRoute
    }
    '/_auth/add': {
      id: '/_auth/add'
      path: '/add'
      fullPath: '/add'
      preLoaderRoute: typeof AuthAddImport
      parentRoute: typeof AuthImport
    }
    '/_layout/game': {
      id: '/_layout/game'
      path: '/game'
      fullPath: '/game'
      preLoaderRoute: typeof LayoutGameImport
      parentRoute: typeof LayoutImport
    }
    '/_layout/ranking': {
      id: '/_layout/ranking'
      path: '/ranking'
      fullPath: '/ranking'
      preLoaderRoute: typeof LayoutRankingImport
      parentRoute: typeof LayoutImport
    }
    '/_auth/profile/reset-password': {
      id: '/_auth/profile/reset-password'
      path: '/profile/reset-password'
      fullPath: '/profile/reset-password'
      preLoaderRoute: typeof AuthProfileResetPasswordImport
      parentRoute: typeof AuthImport
    }
    '/_auth/profile/': {
      id: '/_auth/profile/'
      path: '/profile'
      fullPath: '/profile'
      preLoaderRoute: typeof AuthProfileIndexImport
      parentRoute: typeof AuthImport
    }
  }
}

// Create and export the route tree

export const routeTree = rootRoute.addChildren({
  IndexRoute,
  AuthRoute: AuthRoute.addChildren({
    AuthAddRoute,
    AuthProfileResetPasswordRoute,
    AuthProfileIndexRoute,
  }),
  LayoutRoute: LayoutRoute.addChildren({ LayoutGameRoute, LayoutRankingRoute }),
  LoginRoute,
})

/* prettier-ignore-end */

/* ROUTE_MANIFEST_START
{
  "routes": {
    "__root__": {
      "filePath": "__root.tsx",
      "children": [
        "/",
        "/_auth",
        "/_layout",
        "/login"
      ]
    },
    "/": {
      "filePath": "index.tsx"
    },
    "/_auth": {
      "filePath": "_auth.tsx",
      "children": [
        "/_auth/add",
        "/_auth/profile/reset-password",
        "/_auth/profile/"
      ]
    },
    "/_layout": {
      "filePath": "_layout.tsx",
      "children": [
        "/_layout/game",
        "/_layout/ranking"
      ]
    },
    "/login": {
      "filePath": "login.tsx"
    },
    "/_auth/add": {
      "filePath": "_auth/add.tsx",
      "parent": "/_auth"
    },
    "/_layout/game": {
      "filePath": "_layout/game.tsx",
      "parent": "/_layout"
    },
    "/_layout/ranking": {
      "filePath": "_layout/ranking.tsx",
      "parent": "/_layout"
    },
    "/_auth/profile/reset-password": {
      "filePath": "_auth/profile/reset-password.tsx",
      "parent": "/_auth"
    },
    "/_auth/profile/": {
      "filePath": "_auth/profile/index.tsx",
      "parent": "/_auth"
    }
  }
}
ROUTE_MANIFEST_END */
