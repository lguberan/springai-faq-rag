import React from 'react';
import ReactDOM from 'react-dom/client';
import {ChakraProvider, defaultSystem} from '@chakra-ui/react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';

import App from './App';
import AskPage from './pages/AskPage';
import AdminPage from './pages/AdminPage';

ReactDOM.createRoot(document.getElementById('root')!).render(
    <ChakraProvider value={defaultSystem}>
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<App/>}>
                        <Route index element={<AskPage/>}/>
                        <Route path="admin" element={<AdminPage/>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    </ChakraProvider>
);
