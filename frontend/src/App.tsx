import {Box, Button, Flex} from "@chakra-ui/react";
import {Link, Outlet, useLocation} from "react-router-dom";

const App = () => {
    const location = useLocation();
    const isAdmin = location.pathname.startsWith("/admin");

    return (
        <Box p={4}>
            <Flex justify="center" gap={4} mb={8}>
                <Button asChild variant={isAdmin ? "outline" : "solid"} colorPalette="teal">
                    <Link to="/">Ask</Link>
                </Button>
                <Button asChild variant={isAdmin ? "solid" : "outline"} colorPalette="teal">
                    <Link to="/admin">Admin</Link>
                </Button>
            </Flex>
            <Outlet/>
        </Box>
    );
};
export default App;