import {lazy, Suspense} from 'react';
import {Route, Routes} from 'react-router-dom';

import App from './App';

const AskPage = lazy(() => import('./pages/AskPage'));
const AdminPage = lazy(() => import('./pages/AdminPage'));

const AppRoutes = () => (
    <Suspense fallback={<div role="status">Loading…</div>}>
        <Routes>
            <Route path="/" element={<App/>}>
                <Route index element={<AskPage/>}/>
                <Route path="admin" element={<AdminPage/>}/>
            </Route>
        </Routes>
    </Suspense>
);

export default AppRoutes;
