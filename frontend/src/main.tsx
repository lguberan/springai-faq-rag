import React from 'react';
import ReactDOM from 'react-dom/client';
import {ChakraProvider, defaultSystem} from '@chakra-ui/react';
import {BrowserRouter} from 'react-router-dom';

import AppRoutes from './AppRoutes';

ReactDOM.createRoot(document.getElementById('root')!).render(
    <ChakraProvider value={defaultSystem}>
        <React.StrictMode>
            <BrowserRouter>
                <AppRoutes/>
            </BrowserRouter>
        </React.StrictMode>
    </ChakraProvider>
);
