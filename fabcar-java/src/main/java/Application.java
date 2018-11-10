/*
 * MIT License
 *
 * Copyright (c) 2018 BeeSight Soft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author Nhan Cao <nhan.cao@beesightsoft.com>
 */

import com.nhancv.fabcar.java.AppUser;
import com.nhancv.fabcar.java.FabcarJava;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class);

    public static void main(String args[]) {

        try {
            FabcarJava fabcarJava = new FabcarJava();
            // create fabric-ca client
            HFCAClient caClient = fabcarJava.getHfCaClient("http://localhost:7054", null);

            // enroll or load admin
            AppUser admin = fabcarJava.getAdmin(caClient);
            log.info(admin);

            // register and enroll new user
            AppUser appUser = fabcarJava.getUser(caClient, admin, "hfuser");
            log.info(appUser);

            // get HFC client instance
            HFClient client = fabcarJava.getHfClient();
            // set user context
            client.setUserContext(admin);

            // get HFC channel using the client
            Channel channel = fabcarJava.getChannel(client);
            log.info("Channel: " + channel.getName());

            // call query blockchain example
            fabcarJava.queryBlockChain(client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DONE");

    }

}
